package com.linkedin.android.learning_android_accessibility;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.CustomViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CustomViewActivityTest {
    @Rule
    public ActivityTestRule<CustomViewActivity> mActivityRule =
            new ActivityTestRule<>(CustomViewActivity.class);

    @Test
    public void checkCustomViewIsVisible() {
        onView(withId(R.id.custom_view_simple_custom_view)).check(matches(isDisplayed()));
    }
}
