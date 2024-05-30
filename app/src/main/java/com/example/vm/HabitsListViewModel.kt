package com.example.vm

import Constants
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MyApplication
import com.example.domain.uc.CompleteHabitText
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitType
import com.example.domain.uc.HabitsUseCase
import com.example.doubletapp3.R
import kotlinx.coroutines.launch

class HabitListViewModel(
    val useCase: HabitsUseCase,
): ViewModel() {

    var habitsList: LiveData<List<Habit>> = useCase.habits
    private var filteredList = MutableLiveData<List<Habit>>()
    var badList = MutableLiveData<List<Habit>>()
    var goodList = MutableLiveData<List<Habit>>()
    private var nameFilter: String = ""
    private var sortingFilter: Int = Constants.SORTING.NEW


    fun updateAllHabits() {
        habitsList = useCase.habits
    }

    private fun updateGoodList() {
        goodList.value = filteredList.value?.filter { habit -> habit.type == HabitType.GOOD }
    }

    private fun updateBadList() {
        badList.value = filteredList.value?.filter { habit -> habit.type == HabitType.BAD }
    }

    fun update() {
        filteredList.value = habitsList.value?.filter { habit ->
            habit.title.lowercase().contains(nameFilter.lowercase()) }?.sortedBy { habit ->
            habit.editDate }
        when (sortingFilter) {
            Constants.SORTING.NEW -> {
                filteredList.value = filteredList.value?.reversed()
            }
        }
        updateGoodList()
        updateBadList()
    }


    fun loadHabit() {
        viewModelScope.launch {
            useCase.loadHabitFromServer()
            filteredList.value = habitsList.value
        }
    }

    fun removeHabit(idHabit: String){
        viewModelScope.launch {
            val habit: Habit? = useCase.getHabitById(idHabit)
            if (habit !== null){
                useCase.removeHabit(habit)
            }
        }
    }

    fun completeHabit(idHabit: String) {
        viewModelScope.launch {
            val (textAnswer, argument) = useCase.completeHabit(idHabit)
            val string = MyApplication.getContext()?.getString( DoneHabitText.fromCompleteHabitText(textAnswer).resId,
                argument)
            Toast.makeText(MyApplication.getContext(), string, Toast.LENGTH_SHORT).show()
        }
    }

    fun filter(inputFilter: String, sorting: Int) {
        nameFilter = inputFilter
        sortingFilter = sorting
        update()
    }

    fun getFilteredList(): List<Habit>? {
        return filteredList.value
    }

    fun resetFilters() {
        filter("", Constants.SORTING.NEW)
    }

    fun getFilters(): List<Any> {
        return listOf(nameFilter, sortingFilter)
    }
}

enum class DoneHabitText(val resId: Int){
    BAD_LEFT(R.string.bad_left),
    GOOD_LEFT(R.string.good_left),
    BAD_MORE(R.string.bad_more),
    GOOD_MORE(R.string.good_more),
    ERROR(R.string.default_text);

    companion object {
        fun fromCompleteHabitText(input: CompleteHabitText): DoneHabitText {
            return when(input) {
                CompleteHabitText.BAD_MORE -> BAD_MORE
                CompleteHabitText.BAD_LEFT -> BAD_LEFT
                CompleteHabitText.GOOD_MORE -> GOOD_MORE
                CompleteHabitText.GOOD_LEFT -> GOOD_LEFT
                CompleteHabitText.ERROR -> ERROR
            }
        }
    }
}