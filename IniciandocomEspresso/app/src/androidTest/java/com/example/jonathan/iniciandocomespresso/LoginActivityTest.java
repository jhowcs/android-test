package com.example.jonathan.iniciandocomespresso;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by jonathan on 17/11/16.
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity>
            mActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class, false, true);

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.edtUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.edtPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void whenUsernameIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
        testEmptyFieldState(R.id.edtPassword);
    }

    @Test
    public void whenPasswordIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
        testEmptyFieldState(R.id.edtUsername);
    }

    @Test
    public void whenBothAreFilled_andClickOnLoginButton_shouldOpenMainActivity() {
        Intents.init();

        onView(withId(R.id.edtUsername)).perform(typeText("admin")); closeSoftKeyboard();
        onView(withId(R.id.edtPassword)).perform(typeText("admin")); closeSoftKeyboard();

        Matcher<Intent> matcher = hasComponent(MainActivity.class.getName());

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(matcher).respondWith(result);

        onView(withId(R.id.btnLogin)).perform(click());

        //onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
        intended(matcher);

        Intents.release();
    }

    private void testEmptyFieldState(int field) {
        onView(withId(field)).perform(typeText("admin"));
        closeSoftKeyboard();
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText(R.string.message)).check(matches(isDisplayed()));
        onView(withText(R.string.ok)).perform(click());
    }
}
