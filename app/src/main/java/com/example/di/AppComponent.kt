package com.example.di

import android.content.Context
import com.example.data.di.RepositoryModule
import com.example.domain.uc.HabitsUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun getHabitUseCase(): HabitsUseCase

    fun habitCreateSubComponent(): HabitCreateSubComponent.Builder

    fun habitListSubComponent(): HabitListFactorySubComponent.Builder
}