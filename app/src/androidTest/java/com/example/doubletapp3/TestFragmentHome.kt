package com.example.doubletapp3


import com.kaspersky.kaspresso.screens.KScreen
import com.example.ui.FragmentHome
import io.github.kakaocup.kakao.text.KButton

object TestFragmentHome: KScreen<TestFragmentHome>() {

    private val  createHabitBtn = KButton{ withId(R.id.fab) }

    fun clickAdd() {
        createHabitBtn.click()
    }

    override val layoutId: Int = R.layout.fragment_home

    override val viewClass: Class<*> = FragmentHome::class.java

}