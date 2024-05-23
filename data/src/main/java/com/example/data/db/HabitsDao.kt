package com.example.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Query("SELECT * FROM HabitEntity")
    fun getAll(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM HabitEntity WHERE id LIKE :id")
    fun findById(id: String): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    @Delete
    suspend fun delete(habit: HabitEntity)
}