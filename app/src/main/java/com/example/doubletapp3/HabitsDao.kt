package com.example.doubletapp3

import androidx.lifecycle.LiveData
import androidx.room.*
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id LIKE :id")
    fun finById(id: String): Habit

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Query("DELETE FROM habit")
    fun deleteAll()
}