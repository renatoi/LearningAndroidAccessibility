package com.linkedin.android.learning_android_accessibility;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.AccessibilityChecks;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks
                .enable()
                .setRunChecksFromRootView(true);
    }
    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.linkedin.android.learning_android_accessibility", appContext.getPackageName());
    }
}
