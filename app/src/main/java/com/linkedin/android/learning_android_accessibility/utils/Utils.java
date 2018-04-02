package com.linkedin.android.learning_android_accessibility.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static Date getNearDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, Utils.randomBetween(-48, -1));
        return calendar.getTime();
    }

    public static int randomBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
