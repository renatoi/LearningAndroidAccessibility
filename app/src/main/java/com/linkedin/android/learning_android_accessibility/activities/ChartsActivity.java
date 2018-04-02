package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

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

import com.linkedin.android.learning_android_accessibility.utils.AccessibilityUtils;

public class ChartsActivity extends BaseActivity {

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
    private static void setDataForChart(@NonNull BarLineChartBase chart,
                                        @NonNull float[] data) {
        // Line Data
        if (chart instanceof LineChart) {
            List<Entry> lineEntries = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                lineEntries.add(new Entry(i, data[i]));
            }
            Collections.sort(lineEntries, new EntryXComparator());

            LineDataSet lineDataSet = new LineDataSet(lineEntries, "DataSet");
            lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
            lineDataSets.add(lineDataSet);

            LineData lineData = new LineData(lineDataSets);
            lineData.setValueTextSize(AccessibilityUtils.getScaleIndependentPixels(13f));
            ((LineChart) chart).setData(lineData);
        }
        // Bar data
        else if (chart instanceof BarChart) {
            List<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                barEntries.add(new BarEntry(i, data[i]));
            }

            BarDataSet barDataSet = new BarDataSet(barEntries, "DataSet");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
            barDataSets.add(barDataSet);

            BarData barData = new BarData(barDataSets);
            barData.setValueTextSize(AccessibilityUtils.getScaleIndependentPixels(13f));
            ((BarChart) chart).setData(barData);
        }
    }
}
