package com.linkedin.android.learning_android_accessibility.accessibility;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class ChartAccessibilityHelper extends ExploreByTouchHelper {

    private BarLineChartBase mChart;
    private List<? extends Entry> mEntries;
    private final Rect tempRect = new Rect();

    public interface DataFormatter {
        String getDataDescription(BarLineChartBase chart, List<? extends Entry> entries, Entry entry);
    }
    private DataFormatter mDataFormatter;
    public void setDataFormatter(DataFormatter dataFormatter) {
        mDataFormatter = dataFormatter;
    }

    public ChartAccessibilityHelper(BarLineChartBase chart, List<? extends Entry> entries) {
        super(chart);
        mChart = chart;
        mEntries = entries;
    }

    @Override
    protected int getVirtualViewAt(float x, float y) {
        Entry entry = mChart.getEntryByTouchPoint(x, y);
        final int index = mEntries.indexOf(entry);

        if (index == -1) {
            return ExploreByTouchHelper.INVALID_ID;
        }

        return index;
    }

    @Override
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        final int entrySize = mEntries.size();
        for (int i = 0; i < entrySize; i++) {
            virtualViewIds.add(i);
        }
    }

    @Override
    protected void onPopulateNodeForVirtualView(int virtualViewId, @NonNull AccessibilityNodeInfoCompat node) {
        final Entry entry = mEntries.get(virtualViewId);
        if (entry == null) {
            throw new IllegalArgumentException("Invalid virtual view id");
        }

        // populate the spoken text
        if (mDataFormatter != null) {
            node.setText(mDataFormatter.getDataDescription(mChart, mEntries, entry));
        }

        // bounds
        final Rect bounds = tempRect;

        if (mChart instanceof BarChart) {
            BarChart barChart = (BarChart) mChart;
            RectF barBounds = barChart.getBarBounds((BarEntry) entry);
            barBounds.round(bounds);
            node.setBoundsInParent(bounds);
        } else {
            MPPointF position = mChart.getPosition(entry, YAxis.AxisDependency.LEFT);
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            int pointArea = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, displayMetrics) / 2;

            tempRect.top = (int) position.y - pointArea;
            tempRect.bottom = (int) position.y + pointArea;
            tempRect.left = (int) position.x - pointArea;
            tempRect.right = (int) position.x + pointArea;

            node.setBoundsInParent(bounds);
        }
    }

    @Override
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, @Nullable Bundle arguments) {
        return false;
    }
}
