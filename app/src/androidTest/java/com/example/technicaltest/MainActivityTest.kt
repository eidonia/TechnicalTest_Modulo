package com.example.technicaltest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.technicaltest.ui.mainActivity.MainActivity
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    private var mActivity: MainActivity? = null

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
        assertThat(mActivity, notNullValue())
    }

    @Test
    fun filter_Device() {
        onView(withId(R.id.checkLight)).perform(click())
        onView(withId(R.id.listDevice)).check(matches(hasChildCount(4)))
        onView(withId(R.id.checkLight)).perform(click())
    }

    @Test
    fun filter_Device_And_Remove_One() {
        onView(withId(R.id.checkLight)).perform(click())
        onView(withId(R.id.listDevice)).check(matches(hasChildCount(4)))
        onView(withId(R.id.checkLight)).perform(click())
        onView(withId(R.id.listDevice)).check(matches(hasChildCount(0)))
    }


}