package com.example.di

import com.example.ui.habitinfo.FragmentHabitInfo
import dagger.Subcomponent

@Subcomponent
interface HabitCreateSubComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): HabitCreateSubComponent
    }

    fun inject(habitCreateFragment: FragmentHabitInfo)
}