package com.example.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Habit
import com.example.domain.uc.HabitsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitViewModel @Inject constructor(
    private val useCase: HabitsUseCase
): ViewModel() {
    val habit = MutableLiveData(Habit())

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            useCase.createHabit(habit)
        }
    }

    fun getHabitByName(idHabit: String): Habit? {
        return useCase.getHabitById(idHabit)
    }

    fun set(input: Habit) {
        habit.value = input
    }
}