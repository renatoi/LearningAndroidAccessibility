package com.linkedin.android.learning_android_accessibility;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.learning_android_accessibility.activities.CardsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.linkedin.android.learning_android_accessibility.MatcherUtils.atPosition;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CardsActivityTest {
    @Rule
    public ActivityTestRule<CardsActivity> mActivityRule =
            new ActivityTestRule<>(CardsActivity.class);

    @Test
    public void checkIfCardsHaveImageButtons() {
        onView(withId(R.id.cards_recyclerview))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.cards_card_more_options)))));
        onView(withId(R.id.cards_recyclerview))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.cards_card_like)))));
        onView(withId(R.id.cards_recyclerview))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.cards_card_comment)))));
        onView(withId(R.id.cards_recyclerview))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.cards_card_favorite)))));
        onView(withId(R.id.cards_recyclerview))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.cards_card_share)))));
    }
}
