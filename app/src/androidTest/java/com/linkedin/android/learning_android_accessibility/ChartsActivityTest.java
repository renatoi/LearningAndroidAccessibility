package com.linkedin.android.learning_android_accessibility;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.ChartsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChartsActivityTest {
    @Rule
    public ActivityTestRule<ChartsActivity> mActivityRule =
            new ActivityTestRule<>(ChartsActivity.class);

    @Test
    public void checkChartsAreVisible() {
        onView(withId(R.id.charts_line_chart)).check(matches(isDisplayed()));
        onView(withId(R.id.charts_bar_chart)).check(matches(isDisplayed()));
    }
}
