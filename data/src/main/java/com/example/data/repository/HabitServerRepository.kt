package com.example.data.repository

import com.example.data.db.HabitEntity
import com.example.data.db.HabitsDao
import com.example.data.db.HabitEntity.Companion.fromHabitToEntity
import com.example.data.network.HabitServerAPI
import com.example.data.network.data.HabitDoneServer
import com.example.data.network.data.HabitServer
import com.example.data.network.data.HabitUidServer
import com.example.domain.ApiResponse
import com.example.domain.entity.HabitUid
import com.example.domain.repository.IRepository
import com.example.domain.entity.Habit
import com.example.data.network.retryRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepository @Inject constructor(
    private val habitsDao: HabitsDao,
    private val habitServerAPI: HabitServerAPI
): IRepository {

    override fun getAllHabit(): Flow<List<Habit>> {
        return habitsDao.getAll().map { it.map { it.toHabit() } }
    }

    override suspend fun loadHabitFromServer(): ApiResponse<Unit> {
        return try {
            val response = retryRequest { habitServerAPI.getHabits() }
            response.forEach {
                updateHabitInLocalDatabase(it.toEntity())
            }
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    private suspend fun updateHabitInLocalDatabase(habit: HabitEntity) {
        val habitFromDateBase = habitsDao.findById(habit.id)
        if (habitFromDateBase != null) {
            if (habitFromDateBase.editDate < habit.editDate) {
                habitsDao.update(habit)
            }
        } else {
            habitsDao.insert(habit)
        }
    }

    override fun getHabitById(id: String): Habit? {
        return habitsDao.findById(id)?.toHabit()
    }

    override suspend fun createHabit(habit: Habit): ApiResponse<HabitUid> {
        return try {
            val response = retryRequest {
                habitServerAPI.saveHabit(HabitServer.fromHabit(habit))
            }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            habit.id = apiResponse.data.uid
            updateHabitInLocalDatabase(habit.fromHabitToEntity())
            return apiResponse

        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun editHabit(habit: Habit): ApiResponse<HabitUid> {
        return createHabit(habit)
    }

    override suspend fun removeHabit(habit: Habit): ApiResponse<Unit> {
        return try {
            retryRequest { habitServerAPI.removeHabit(HabitUidServer(habit.id!!)) }
            habitsDao.delete(habit.fromHabitToEntity())
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun doneHabit(habit: Habit): ApiResponse<Unit> {
        return try {
            retryRequest { habitServerAPI.doneHabit(HabitDoneServer(habit.doneDates.last(), habit.id!!)) }
            habitsDao.update(habit.fromHabitToEntity())
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }
}