package com.example.di

import com.example.ui.habitslist.FragmentHabitsList
import dagger.Subcomponent

@Subcomponent
interface HabitListFactorySubComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): HabitListFactorySubComponent
    }

    fun inject(habitListFragment: FragmentHabitsList)
}
