package com.example.data.di

import android.content.Context
import com.example.data.db.HabitsDB
import com.example.data.db.HabitsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(database: HabitsDB): HabitsDao {
        return database.habitsDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): HabitsDB {
        return HabitsDB.buildDataBase(context)
    }

}