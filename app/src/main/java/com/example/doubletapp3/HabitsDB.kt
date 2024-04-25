package com.example.doubletapp3


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDB: RoomDatabase() {

    companion object {
        fun getDB(context: Context): HabitsDB{
            return Room.databaseBuilder(
                context.applicationContext,
                HabitsDB::class.java,
                "HABITS"
            ).allowMainThreadQueries().build()
        }
    }

    abstract fun habitsDao(): HabitsDao
}