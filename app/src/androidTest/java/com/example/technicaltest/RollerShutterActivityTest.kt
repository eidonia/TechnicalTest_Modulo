package com.example.technicaltest

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.technicaltest.ui.heaterPage.HeaterActivity
import com.example.technicaltest.ui.rollerShutterPage.RollerShutterActivity
import com.google.android.material.slider.Slider
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RollerShutterActivityTest {
    @get:Rule
    var mActivityRule: ActivityTestRule<RollerShutterActivity> = object : ActivityTestRule<RollerShutterActivity>(
        RollerShutterActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, RollerShutterActivity::class.java).apply {
                putExtra("RollerShutterID", 2)
            }
        }
    }
    private lateinit var mActivity: RollerShutterActivity

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
        ViewMatchers.assertThat(mActivity, CoreMatchers.notNullValue())
    }

    @Test
    fun seeSlider() {
        onView(withId(R.id.slider)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.slider)).perform(setValue(10f))
        onView(withId(R.id.slider)).perform(setValue(100f))
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