package com.linkedin.android.learning_android_accessibility.utils;

import android.content.res.Resources;
import android.util.TypedValue;

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
}
