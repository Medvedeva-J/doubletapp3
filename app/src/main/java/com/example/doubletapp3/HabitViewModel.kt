package com.example.doubletapp3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitsDao = HabitsDB.getInstance().habitsDao()
    private val client = Client.getHttpClient()
    private val habitServerAPI = Client.getHabitServerAPI(client)
    val habit = MutableLiveData<Habit>()

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            postToServer(habit)
        }
    }

    private suspend fun postToServer(habit: Habit): APIResponse<HabitUid> {
        return try {
            val response = retryRequest {
                habitServerAPI.saveHabit(HabitServer.fromHabit(habit))
            }

            val apiResponse = APIResponse.Success(HabitUid(response.uid))

            habit.id = apiResponse.data.uid
            updateHabitInLocalDatabase(HabitEntity.fromHabit(habit))
            return apiResponse

        } catch (e: RuntimeException) {
            APIResponse.Error(e)
        }
    }

    private suspend fun <T> retryRequest(delay: Long = 1000, func: suspend () -> T): T {
        val toast = Toast.makeText(MyApplication.appContext, "Can't save your habit. Next try in 1 sec", Toast.LENGTH_SHORT)
        while(true) {
            try {
                return func()
            } catch (e: Exception) {
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

    fun set(input: Habit) {
        habit.value = input
    }
}