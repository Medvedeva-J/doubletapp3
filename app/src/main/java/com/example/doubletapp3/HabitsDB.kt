package com.example.doubletapp3


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitsDB: RoomDatabase() {

    companion object {

        @Volatile private var INSTANCE: HabitsDB? = null

        fun getInstance(): HabitsDB{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase().also { INSTANCE = it }
            }
        }

        private fun buildDataBase(): HabitsDB{
            return Room.databaseBuilder(
                MyApplication.appContext,
                HabitsDB::class.java,
                "HABITS"
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun habitsDao(): HabitsDao
}