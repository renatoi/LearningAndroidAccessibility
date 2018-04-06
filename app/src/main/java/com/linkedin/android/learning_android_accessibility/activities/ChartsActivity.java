package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.linkedin.android.learning_android_accessibility.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.linkedin.android.learning_android_accessibility.accessibility.ChartAccessibilityHelper;
import com.linkedin.android.learning_android_accessibility.utils.AccessibilityUtils;

public class ChartsActivity extends BaseActivity implements ChartAccessibilityHelper.DataFormatter {

    private List<Entry> mLineEntries;
    private List<BarEntry> mBarEntries;

    private ChartAccessibilityHelper mLineHelper;
    private ChartAccessibilityHelper mBarHelper;

    public static Intent newIntent(Context context) {
        return new Intent(context, ChartsActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_charts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // charts ref
        LineChart lineChart = findViewById(R.id.charts_line_chart);
        BarChart barChart = findViewById(R.id.charts_bar_chart);

        // configure charts
        IAxisValueFormatter xValueFormatter = getValueFormatterForMonth();
        configureChart(lineChart, xValueFormatter);
        configureChart(barChart, xValueFormatter);

        // populate charts with random numbers
        float[] data = getRandomFloats(6, 500);
        setDataForChart(lineChart, data);
        setDataForChart(barChart, data);

        // set accessibility delegate
        mLineHelper = new ChartAccessibilityHelper(lineChart, mLineEntries);
        mLineHelper.setDataFormatter(this);
        ViewCompat.setAccessibilityDelegate(lineChart, mLineHelper);

        mBarHelper = new ChartAccessibilityHelper(barChart, mBarEntries);
        mBarHelper.setDataFormatter(this);
        ViewCompat.setAccessibilityDelegate(barChart, mBarHelper);

        // send hover events to the helper
        lineChart.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                return mLineHelper.dispatchHoverEvent(event);
            }
        });
        barChart.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                return mBarHelper.dispatchHoverEvent(event);
            }
        });
    }

    /**
     * Creates a month value formatter for a chart axis
     * @return The month value formatter
     */
    private static IAxisValueFormatter getValueFormatterForMonth() {
        return new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, Math.round(value) % 12);
                return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
            }
        };
    }

    /**
     * Returns an array of random floats given a count and a max value
     * @param count The number of floats to return
     * @param maxValue The max value for the float
     * @return An array of floats with random values
     */
    private static float[] getRandomFloats(int count, float maxValue) {
        float[] floats = new float[count];
        for (int i = 0; i < count; i++) {
            float mult = (maxValue + 1);
            floats[i] = (float) (Math.round(Math.random() * mult));
        }
        return floats;
    }

    /**
     * Configures chart options such as enabling x and y axis, legend, appeareance, and behaviour
     * @param chart The chart to be configured.
     * @param xAxisValueFormatter The value formatter for the xAxis
     */
    private static void configureChart(@NonNull BarLineChartBase chart,
                                @NonNull IAxisValueFormatter xAxisValueFormatter) {
        // Configure chart
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);

        // X axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(xAxisValueFormatter);

        // Left axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);

        // Right axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // Legend
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
    }

    /**
     * Set data for a line or bar chart
     * @param chart The chart to set data to
     * @param data And array of floats
     */
    private void setDataForChart(@NonNull BarLineChartBase chart,
                                        @NonNull float[] data) {
        // Line Data
        if (chart instanceof LineChart) {
            mLineEntries = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                mLineEntries.add(new Entry(i, data[i]));
            }
            Collections.sort(mLineEntries, new EntryXComparator());

            LineDataSet lineDataSet = new LineDataSet(mLineEntries, "DataSet");
            lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
            lineDataSets.add(lineDataSet);

            LineData lineData = new LineData(lineDataSets);
            lineData.setValueTextSize(AccessibilityUtils.getScaleIndependentPixels(13f));
            ((LineChart) chart).setData(lineData);
        }
        // Bar data
        else if (chart instanceof BarChart) {
            mBarEntries = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                mBarEntries.add(new BarEntry(i, data[i]));
            }

            BarDataSet barDataSet = new BarDataSet(mBarEntries, "DataSet");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
            barDataSets.add(barDataSet);

            BarData barData = new BarData(barDataSets);
            barData.setValueTextSize(AccessibilityUtils.getScaleIndependentPixels(13f));
            ((BarChart) chart).setData(barData);
        }
    }

    @Override
    public String getDataDescription(BarLineChartBase chart, List<? extends Entry> entries, Entry entry) {
        String xValue = chart.getXAxis().getFormattedLabel(entries.indexOf(entry));
        int yValue = Math.round(entry.getY());

        return getResources().getQuantityString(R.plurals.charts_plurals_data_description, yValue, xValue, yValue);
    }
}
