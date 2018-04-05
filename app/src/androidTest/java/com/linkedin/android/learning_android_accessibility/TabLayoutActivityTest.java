package com.linkedin.android.learning_android_accessibility;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.linkedin.android.learning_android_accessibility.activities.TabLayoutActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TabLayoutActivityTest {
    @Rule
    public ActivityTestRule<TabLayoutActivity> mActivityRule =
            new ActivityTestRule<>(TabLayoutActivity.class);

    @Test
    public void checkViewPagerSwipe() {
        onView(withId(R.id.tab_layout_view_pager))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tab_layout_view_pager))
                .perform(swipeLeft());
    }

    @Test
    public void checkTabClickAndContentDisplay() {
        Matcher<View> matcher = allOf(withText("Learn"),
                isDescendantOfA(withId(R.id.tab_layout_tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800); // Wait a little until the content is loaded
        onView(allOf(withId(R.id.tab_layout_textview), isCompletelyDisplayed()))
                .check(matches(withText(containsString("Learn more"))));
    }
}
