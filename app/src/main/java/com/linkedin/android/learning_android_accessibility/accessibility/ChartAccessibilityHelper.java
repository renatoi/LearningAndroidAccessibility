package com.linkedin.android.learning_android_accessibility.accessibility;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.accessibility.AccessibilityEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

/**
 * The following class provides accessibility support for charts.
 * Extend ExploreByTouchHelper and implement the following 5 methods described below:
 */
public class ChartAccessibilityHelper extends ExploreByTouchHelper {

    // No item found in data
    private static final int NO_ITEM = -1;

    // Touch area for mChart points in DP, minimum recommended is 48
    private static final int POINT_AREA = 48;

    private BarLineChartBase mChart;
    private List<? extends Entry> mEntries;
    private final Rect tempRect = new Rect();

    // Consumer can format the content description of the data point
    private DataFormatter mDataFormatter;
    public void setDataFormatter(DataFormatter dataFormatter) {
        mDataFormatter = dataFormatter;
    }
    public interface DataFormatter {
        String getDataDescription(BarLineChartBase chart, List<? extends Entry> entries, Entry entry);
    }

    public ChartAccessibilityHelper(BarLineChartBase mChart, List<? extends Entry> mEntries) {
        super(mChart);
        this.mChart = mChart;
        this.mEntries = mEntries;
    }

    @Override
    protected int getVirtualViewAt(float x, float y) {
        /*
         * This method handles which virtual view should be selected when the user taps on
         * these X and Y coordinates.
         *
         * Each data point needs a virtual view id. The id is the index of each data point.
         */
        Entry entry = mChart.getEntryByTouchPoint(x, y);
        final int index = mEntries.indexOf(entry);

        if (index == NO_ITEM) {
            return ExploreByTouchHelper.INVALID_ID;
        }

        return index;
    }

    @Override
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        /*
         * This method asks you to add to the list argument provided the virtual views that are
         * visible. Since every item should be visible, and since we're mapping directly from
         * item index to virtual view id, we can just add every available index in the item
         * list.
         */
        final int n = mEntries.size();
        for (int i = 0; i < n; i++) {
            virtualViewIds.add(i);
        }
    }

    @Override
    protected void onPopulateEventForVirtualView(int virtualViewId, @NonNull AccessibilityEvent event) {
        /*
         * This method handles what is spoken when an accessibility event is fired by
         * allowing you to populate the accessibility event with more info.
         * Accessibility events are sent by the system when something notable happens in
         * the user interface. For example, when a Button is clicked, a View is focused, etc.
         * An AccessibilityService (such as TalkBack or BrailleBack) can then inspect this
         * event's properties to get more information about its state. For example, a button
         * was clicked and something must be spoken. The info to be spoken is stored in the
         * event object.
         */
        final Entry entry = mEntries.get(virtualViewId);

        if (entry == null) {
            throw new IllegalArgumentException("Invalid virtual view id");
        }

        // The event must be populated with text, either using
        // getText().add() or setContentDescription(). Since the item's
        // description is displayed visually, we'll add it to the event
        // text. If it was only used for accessibility, we would use
        // setContentDescription().
        if (mDataFormatter != null) {
            event.getText().add(mDataFormatter.getDataDescription(mChart, mEntries, entry));
        }
    }

    @Override
    protected void onPopulateNodeForVirtualView(int virtualViewId,
                                                AccessibilityNodeInfoCompat node) {
        /*
         * This method allows you to populate the virtual view with more information. This
         * information is stored in the AccessibilityNodeInfo object. It contains all the
         * information that an accessibility service needs if it requests for more
         * information (usually not contained in an AccessibilityEvent).
         *
         * What we mainly populate here are:
         *
         *    - the boundaries of the virtual view: this is used to create the focus
         *      highlight around the virtual view.
         *    - the actions that can be performed on this virtual view, for example, click,
         *      scroll, or even custom actions (that can be reached via accessibility service
         *      shortcuts).
         *    - The spoken text when this view has focus. This is similar to the
         *      accessibility event information. The main difference is that the
         *      AccessibilityEvent is a small pay-load that gets broadcasted for accessibility
         *      events. NodeInfo contains more info about the view, including the spoken text.
         */

        final Entry entry = mEntries.get(virtualViewId);
        if (entry == null) {
            throw new IllegalArgumentException("Invalid virtual view id");
        }

        /*
         * Node and event text and content descriptions are usually identical, so we'll use the
         * exact same string as before.
         */
        if (mDataFormatter != null) {
            node.setText(mDataFormatter.getDataDescription(mChart, mEntries, entry));
        }

        /*
         * Reported bounds should be consistent with those used to draw the item in onDraw().
         * They should also be consistent with the hit detection performed in getVirtualViewAt()
         * and onTouchEvent().
         */
        final Rect bounds = tempRect;

        if (mChart instanceof BarChart) {
            // Bar Chart
            BarChart barChart = (BarChart) mChart;
            RectF barBounds = barChart.getBarBounds((BarEntry) entry);
            barBounds.round(bounds);
            node.setBoundsInParent(bounds);
        } else {
            // Line Chart
            MPPointF position = mChart.getPosition(entry, YAxis.AxisDependency.LEFT);

            // Convert DP to PX
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            int pointArea = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    POINT_AREA, Resources.getSystem().getDisplayMetrics()) / 2;

            tempRect.top = (int) position.y - pointArea;
            tempRect.bottom = (int) position.y + pointArea;
            tempRect.left = (int) position.x - pointArea;
            tempRect.right = (int) position.x + pointArea;
            node.setBoundsInParent(bounds);
        }
    }

    @Override
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
        // Use this method if you need to provide a click event for the view.
        // Example:
        // switch (action) {
        //     case AccessibilityNodeInfoCompat.ACTION_CLICK:
        //         return onItemClicked(virtualViewId);
        // }
        return false;
    }
}
