package com.example.data.di

import com.example.data.db.HabitsDao
import com.example.data.di.DatabaseModule
import com.example.data.network.HabitServerAPI
import com.example.data.repository.HabitRepository
import com.example.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(habitDao: HabitsDao, habitAPI: HabitServerAPI): IRepository {
        return HabitRepository(habitDao, habitAPI)
    }

}