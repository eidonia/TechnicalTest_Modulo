package com.example.technicaltest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.technicaltest.ui.mainActivity.MainActivity
import com.example.technicaltest.ui.settings.UserSettings
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<UserSettings> = ActivityTestRule(UserSettings::class.java)
    private lateinit var mActivity: UserSettings

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
        ViewMatchers.assertThat(mActivity, CoreMatchers.notNullValue())
    }

    @Test
    fun open_Modifiy() {
        onView(withId(R.id.btnModify)).perform(click())
        onView(withId(R.id.btnModify)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        onView(withId(R.id.textFirstName)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}