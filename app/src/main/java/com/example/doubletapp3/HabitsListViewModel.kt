package com.example.doubletapp3

import Constants
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HabitsListViewModel: ViewModel() {
    private val client = Client.getHttpClient()
    private val habitServerAPI = Client.getHabitServerAPI(client)
    private val habitsDao = HabitsDB.getInstance().habitsDao()

    private var habitsList = MutableLiveData<MutableList<Habit>>()
    private var filteredList = MutableLiveData<List<Habit>>()
    var badList = MutableLiveData<List<Habit>>()
    var goodList = MutableLiveData<List<Habit>>()
    private var nameFilter: String = ""
    private var sortingFilter: Int = 1

    init {
        loadHabit()
        filteredList.value = habitsList.value
    }

    private fun updateGoodList() {
        goodList.value = filteredList.value?.filter { habit -> habit.type == HabitType.GOOD }
    }

    private fun updateBadList() {
        badList.value = filteredList.value?.filter { habit -> habit.type == HabitType.BAD }
    }

    fun update() {
        filteredList.value = habitsList.value?.filter { habit ->
            habit.title.contains(nameFilter) }?.sortedBy { habit ->
            habit.edit_date }
        when (sortingFilter) {
            Constants.SORTING.NEW -> {
                filteredList.value = filteredList.value?.reversed()
            }
        }
        updateGoodList()
        updateBadList()
    }

    fun updateAllHabits(newList: List<HabitEntity>) {
        habitsList.value = newList.map { it.toHabit() } as MutableList<Habit>
    }

    fun filter(inputFilter: String, sorting: Int) {
        nameFilter = inputFilter
        sortingFilter = sorting
        update()
    }

    private suspend fun loadFromServer(): APIResponse<Unit> {
        return try {
            val response = retryRequest { habitServerAPI.getHabits() }
            response.forEach{
                updateHabitInLocalDatabase(it.toEntity())
            }
            APIResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            APIResponse.Error(e)
        }
    }

    private suspend fun removeFromServer(habit: Habit): APIResponse<Unit> {
        return try {
            retryRequest { habitServerAPI.removeHabit(HabitUidServer(habit.id!!)) }
            habitsDao.delete(HabitEntity.fromHabit(habit))
            APIResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            APIResponse.Error(e)
        }
    }

    private fun loadHabit() {
        viewModelScope.launch(CoroutineName("load habit")) {
            loadFromServer()
        }
    }

    private suspend fun <T> retryRequest(delay: Long = 1000, func: suspend () -> T): T {
        val toast = Toast.makeText(MyApplication.appContext, "\"${func.javaClass.enclosingMethod?.name}\" caught an error. Next try in 1 sec", Toast.LENGTH_SHORT)
        while(true) {
            try {
                return func()
            } catch (e: Exception) {
                Log.e("RetryRequest", func.javaClass.enclosingMethod?.name, e)
                toast.show()
            }
            delay(delay)
            toast.cancel()
        }
    }

    private suspend fun updateHabitInLocalDatabase(habit: HabitEntity){
        val habitFromDateBase = habit.id.let { habitsDao.findById(it) }
        if (habitFromDateBase != null) {
            if (habitFromDateBase.edit_date < habit.edit_date){
                habitsDao.update(habit)
            }
        } else {
            habitsDao.insert(habit)
        }
    }

    suspend fun removeHabit(id: String){
        viewModelScope.launch(CoroutineName("delete habit")){
            val habit: Habit? = habitsDao.findById(id)?.toHabit()
            if (habit !== null){
                removeFromServer(habit)
            }
        }
    }
}