package com.linkedin.android.learning_android_accessibility.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AccessibilityUtils {
    /**
     * Returns scale-independent pixels (respects user's text size preferences). Always use this
     * when setting text sizes programmatically.
     * @param dp: the size of the font in dp
     * @return sp: the scale independent pixels
     */
    public static float getScaleIndependentPixels(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return dp * (1 - (density - scaledDensity));
    }

    /**
     * Converts sp to px
     * @param sp Sp value
     * @return px The value in pixels
     */
    public static float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * Iterate over all subviews and get content description or textview
     * @param view The view to iterate
     * @return The list of descriptions
     */
    public static List<CharSequence> getDescriptions(View view) {
        List<CharSequence> descriptions = new ArrayList<>();

        CharSequence contentDescription = view.getContentDescription();
        if (!TextUtils.isEmpty(contentDescription)) {
            descriptions.add(contentDescription);
            return descriptions;
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                descriptions.addAll(getDescriptions(viewGroup.getChildAt(i)));
            }
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            descriptions.add(textView.getText());
        }

        return descriptions;
    }
}
