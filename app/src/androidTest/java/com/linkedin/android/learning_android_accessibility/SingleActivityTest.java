package com.linkedin.android.learning_android_accessibility;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.SingleActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SingleActivityTest {
    @Rule
    public ActivityTestRule<SingleActivity> mActivityRule =
            new ActivityTestRule<>(SingleActivity.class);

    @Test
    public void checkDetailIsDisplayedOnItemClicked() {
        onView(ViewMatchers.withId(R.id.single_list_recyclerview))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Option 15"))))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Option 15")), click()));
        onView(withText("Detail fragment")).check(matches(isCompletelyDisplayed()));
    }
}
