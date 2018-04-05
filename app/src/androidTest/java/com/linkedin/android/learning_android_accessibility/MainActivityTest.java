package com.linkedin.android.learning_android_accessibility;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIfButtonsAreClickableAndFocusable() {
        onView(withId(R.id.main_login_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_settings_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_tablayout_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_single_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_cards_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_custom_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
        onView(withId(R.id.main_charts_button))
                .check(matches(isFocusable()))
                .check(matches(isClickable()));
    }
}
