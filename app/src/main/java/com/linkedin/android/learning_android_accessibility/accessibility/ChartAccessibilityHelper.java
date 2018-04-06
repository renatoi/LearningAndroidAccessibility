package com.linkedin.android.learning_android_accessibility.accessibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

public class ChartAccessibilityHelper extends ExploreByTouchHelper {

    private BarLineChartBase mChart;
    private List<? extends Entry> mEntries;

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

    }

    @Override
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, @Nullable Bundle arguments) {
        return false;
    }
}
