package com.example.bulletinboard;

import android.content.ClipData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by alexandre on 05/11/17.
 */

@RunWith(AndroidJUnit4.class)
public class PostCreateTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Test
    public void addAPostAndLoadIt(){
        onView(withId(R.id.createPostButton)).perform(click());
        onView(withId(R.id.title)).perform(typeText("Automated Post Test"), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(typeText("Post Description"), closeSoftKeyboard());
        onView(withId(R.id.button_finish)).perform(click());
        pressBack();
        onView(withId(R.id.viewPostsButton)).perform(click());
       /* onData(anything()).inAdapterView(withId(R.id.post_list)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.post_list)).atPosition(0).check(matches(withText("Automated Post Test")));*/
    }
}