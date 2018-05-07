package com.tammidev.day2daybudget.budget.configure

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.budget.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigureFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun before() {
        onView(withId(R.id.action_configure)).perform(click());
    }

    @Test
    fun test() {
        onView(withId(R.id.amountEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.amountEditText)).perform(typeText("20.00"))
        onView(withId(R.id.nameEditText)).perform(typeText("Test"))

        closeSoftKeyboard()
        onView(withId(R.id.saveBtn)).perform(click())

        onView(withId(R.id.action_overview)).perform(click())
        onView(withText("Test")).check(matches(isDisplayed()))
    }
}