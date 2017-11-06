package com.example.bulletinboard;

import android.content.ClipData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
public class PostListTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Test
    public void ensurePostsAreLoaded(){
        onView(withId(R.id.viewPostsButton)).perform(click());
       /* onData(anything()).inAdapterView(withId(R.id.post_list)).check(matches(isDisplayed()));*/
    }

}
