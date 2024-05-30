package com.example.doubletapp3

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.scroll.KScrollView
import com.example.ui.FragmentHome
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object TestFragmentHabitInfo: KScreen<TestFragmentHabitInfo>() {

    private val submitBtn = KButton { withId(R.id.submit_habit_button) }
    private val titleInput = KEditText { withId(R.id.title_input) }
    private val descriptionInput = KEditText { withId(R.id.description_input) }
    private val badType = KButton { withId(R.id.option2) }
    private val goodType = KButton { withId(R.id.option1) }
    private val priority = KView { withId(R.id.priority_selector) }
    private val daysInput = KEditText { withId(R.id.days_input) }
    private val repeatInput = KEditText { withId(R.id.repeat_input) }
    private val colorPicker = KScrollView { withId(R.id.colorpicker) }

    fun fillForm() {
        titleInput.typeText("Habit sample name")
        descriptionInput.typeText("Habit sample description")
        badType.click()
        priority.isVisible()
        daysInput.isVisible()
        daysInput.hasAnyText()
        repeatInput.isVisible()
        repeatInput.hasAnyText()
        Espresso.closeSoftKeyboard()
        colorPicker.isVisible()
        onView(withPositionInParent(R.id.color_radiogroup, 0)).check { view, _ ->
            view.performClick()
        }
        goodType.click()
    }

    fun clickSubmit() {
        submitBtn.click()
    }

    override val layoutId: Int = R.layout.fragment_home

    override val viewClass: Class<*> = FragmentHome::class.java

    private fun withPositionInParent(parentViewId: Int, position: Int): Matcher<View> {
        return allOf(withParent(withId(parentViewId)), withParentIndex(position))
    }
}