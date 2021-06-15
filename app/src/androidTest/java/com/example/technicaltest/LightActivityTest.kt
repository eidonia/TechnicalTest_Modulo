package com.example.technicaltest

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.technicaltest.ui.lightPage.LightActivity
import com.example.technicaltest.ui.mainActivity.MainActivity
import com.google.android.material.slider.Slider
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LightActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<LightActivity> = object : ActivityTestRule<LightActivity>(LightActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, LightActivity::class.java).apply {
                putExtra("LightID", 1)
            }
        }
    }
    private lateinit var mActivity: LightActivity

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
        assertThat(mActivity,notNullValue())
    }

    @Test
    fun seeSlider() {
        onView(withId(R.id.sliderLight)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.sliderLight)).perform(setValue(10f))
        onView(withId(R.id.sliderLight)).perform(setValue(100f))
    }
    @Test
    fun turn_On_turn_Off() {
        onView(withId(R.id.btnOff)).perform(click())
        onView(withId(R.id.sliderLight)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.btnOn)).perform(click())
        onView(withId(R.id.sliderLight)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    fun withValue(expectedValue: Float): Matcher<View?> {
        return object : BoundedMatcher<View?, Slider>(Slider::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("expected: $expectedValue")
            }

            override fun matchesSafely(slider: Slider?): Boolean {
                return slider?.value == expectedValue
            }
        }
    }

    fun setValue(value: Float): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Set Slider value to $value"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(Slider::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                val seekBar = view as Slider
                seekBar.value = value
            }
        }
    }
}