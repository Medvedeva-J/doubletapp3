package com.example.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [HabitEntity::class], version = 2)
abstract class HabitsDB: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao

    companion object {
        private const val DATABASE_NAME = "HABITS"

        fun buildDataBase(context: Context): HabitsDB {
            return Room.databaseBuilder(
                context.applicationContext,
                HabitsDB::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .build()
        }
    }
}