package com.example.domain.uc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.domain.ApiResponse
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitType
import com.example.domain.repository.IRepository
import java.util.*
import javax.inject.Inject

class HabitsUseCase @Inject constructor(
    private val repository: IRepository
) {

    val habits: LiveData<List<Habit>> = repository.getAllHabit().asLiveData()

    suspend fun loadHabitFromServer(){
        val apiResponse = repository.loadHabitFromServer()
        if (apiResponse is ApiResponse.Error){
            Log.d("loadHabitFromServer", "Error")
        }
    }

    suspend fun createHabit(habit: Habit){
        val apiResponse = repository.createHabit(habit)
        if (apiResponse is ApiResponse.Error){
            Log.d("createHabit", "Error")
        }
    }

    suspend fun removeHabit(habit: Habit){
        val apiResponse = repository.removeHabit(habit)
        if (apiResponse is ApiResponse.Error){
            Log.d("removeHabit", "Error")
        }
    }

    fun getHabitById(nameId: String): Habit?{
        return repository.getHabitById(nameId)
    }

    private fun updateDoneDate(habit: Habit): Habit{
        val secInDay: Int = 60 * 60 * 24
        val secInPeriod = habit.days * secInDay
        val nowTime: Int = (Date().time / 1000).toInt()

        if (habit.editDate <= nowTime - secInPeriod){
            habit.doneDates = listOf(nowTime)
            habit.editDate = nowTime
        } else {
            habit.doneDates = habit.doneDates + listOf(nowTime)
        }

        return habit
    }

    private suspend fun doneOnServer(habit: Habit){
        val habitAfterUpdate = updateDoneDate(habit)

        val apiResponse = repository.doneHabit(habitAfterUpdate)
        if (apiResponse is ApiResponse.Error){
            Log.d("doneHabit", "Error")
        }
    }

    private fun getExecutionsLeft(habit: Habit): Int {
        val countBeDone = habit.doneDates.count()
        return habit.repeat - countBeDone
    }

    suspend fun completeHabit(idHabit: String): Pair<CompleteHabitText, Int?> {
        val habit: Habit = getHabitById(idHabit)!!

        doneOnServer(habit)

        val remainingExec = getExecutionsLeft(habit)
        if (habit.type == HabitType.BAD){
            if (remainingExec > 0) {
                return Pair(CompleteHabitText.BAD_LEFT, remainingExec)
            } else {
                return Pair(CompleteHabitText.BAD_MORE, null)
            }

        } else if (habit.type == HabitType.GOOD){
            if (remainingExec > 0){
                return Pair(CompleteHabitText.GOOD_LEFT, remainingExec)
            } else {
                return Pair(CompleteHabitText.GOOD_MORE, null)
            }
        }
        return Pair(CompleteHabitText.ERROR, null)
    }

}

enum class CompleteHabitText{
    BAD_LEFT,
    GOOD_LEFT,
    BAD_MORE,
    GOOD_MORE,
    ERROR,
}