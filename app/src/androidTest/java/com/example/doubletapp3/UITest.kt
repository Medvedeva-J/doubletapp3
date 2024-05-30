package com.example.doubletapp3

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.ui.ActivityMain
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test


class UITest: TestCase() {

    @get:Rule
    val activity = activityScenarioRule<ActivityMain>()

    @Test
    fun test() {
        TestFragmentHome.clickAdd()
        TestFragmentHabitInfo.fillForm()
        TestFragmentHabitInfo.clickSubmit()
    }

}