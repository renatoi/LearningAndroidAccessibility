package com.linkedin.android.learning_android_accessibility.utils;

import android.content.res.Resources;

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
}
