package com.example.domain.repository

import com.example.domain.ApiResponse
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitUid
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllHabit(): Flow<List<Habit>>

    suspend fun loadHabitFromServer() : ApiResponse<Unit>

    fun getHabitById(id: String): Habit?

    suspend fun createHabit(habit: Habit): ApiResponse<HabitUid>

    suspend fun editHabit(habit: Habit): ApiResponse<HabitUid>

    suspend fun removeHabit(habit: Habit) : ApiResponse<Unit>

    suspend fun doneHabit(habit: Habit): ApiResponse<Unit>
}