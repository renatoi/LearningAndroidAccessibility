package com.linkedin.android.learning_android_accessibility;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.linkedin.android.learning_android_accessibility.MatcherUtils.getTextInputLayoutEditText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkGlobalErrorMessageIsDisplayed() {
        onView(getTextInputLayoutEditText(R.id.login_email_address))
                .perform(typeText("john@example.com"));
        onView(getTextInputLayoutEditText(R.id.login_password))
                .perform(typeText("password"));
        onView(withId(R.id.login_login_button))
                .perform(click());
        onView(withId(R.id.login_error_message))
                .check(matches(not(withText(""))));
    }
}
