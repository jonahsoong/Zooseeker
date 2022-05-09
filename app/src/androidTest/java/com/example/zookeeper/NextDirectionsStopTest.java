package com.example.zookeeper;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NextDirectionsStopTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fullAppTraversalTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)))
                .atPosition(4);
        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_button), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.generate_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.get_directions_btn), withText("Get Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.direction_name_text), withText("Proceed on Entrance Way 10.0 ft towards Entrance Plaza"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed on Entrance Way 10.0 ft towards Entrance Plaza")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.direction_name_text), withText("Continue on Reptile Road 100.0 ft towards Alligators"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView2.check(matches(withText("Continue on Reptile Road 100.0 ft towards Alligators")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.Nextbutton), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.direction_name_text), withText("Proceed on Sharp Teeth Shortcut 200.0 ft towards Lions"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed on Sharp Teeth Shortcut 200.0 ft towards Lions")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.direction_name_text), withText("Continue on Africa Rocks Street 200.0 ft towards Elephant Odyssey"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView4.check(matches(withText("Continue on Africa Rocks Street 200.0 ft towards Elephant Odyssey")));

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.Nextbutton), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.direction_name_text), withText("Proceed on Africa Rocks Street 200.0 ft towards Lions"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView6.check(matches(withText("Proceed on Africa Rocks Street 200.0 ft towards Lions")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.direction_name_text), withText("Continue on Sharp Teeth Shortcut 200.0 ft towards Alligators"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView7.check(matches(withText("Continue on Sharp Teeth Shortcut 200.0 ft towards Alligators")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.direction_name_text), withText("Continue on Reptile Road 100.0 ft towards Entrance Plaza"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView8.check(matches(withText("Continue on Reptile Road 100.0 ft towards Entrance Plaza")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.direction_name_text), withText("Continue on Entrance Way 10.0 ft towards Entrance and Exit Gate"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView9.check(matches(withText("Continue on Entrance Way 10.0 ft towards Entrance and Exit Gate")));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.Nextbutton), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.Nextbutton), withText("NEXT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isNotEnabled()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
