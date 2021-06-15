package com.example.technicaltest

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.technicaltest.ui.heaterPage.HeaterActivity
import com.example.technicaltest.ui.lightPage.LightActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HeaterActivityTest {
    @get:Rule
    var mActivityRule: ActivityTestRule<HeaterActivity> = object : ActivityTestRule<HeaterActivity>(
        HeaterActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, HeaterActivity::class.java).apply {
                putExtra("HeaterID", 3)
            }
        }
    }
    private lateinit var mActivity: HeaterActivity

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
        ViewMatchers.assertThat(mActivity, CoreMatchers.notNullValue())
    }

    @Test
    fun hot_And_Cold_Button() {
        onView(withId(R.id.btnOn)).perform(click())
        onView(withId(R.id.txtTemp)).check(matches(withText("20.0°")))
        onView(withId(R.id.btnHot)).perform(click())
        onView(withId(R.id.txtTemp)).check(matches(withText("20.5°")))
        onView(withId(R.id.btnCold)).perform(click())
        onView(withId(R.id.btnCold)).perform(click())
        onView(withId(R.id.btnCold)).perform(click())
        onView(withId(R.id.txtTemp)).check(matches(withText("19.0°")))
    }
}