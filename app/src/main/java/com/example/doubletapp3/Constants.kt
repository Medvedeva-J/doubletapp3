
import android.content.res.Resources
import android.health.connect.datatypes.units.BloodGlucose
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.doubletapp3.R


object Constants {
    const val REQUEST_HABIT_CREATED : Int = 1
    const val REQUEST_HABIT_EDITED : Int = 2

    const val KEY_HABIT_INFO:String = "habit-info"
    const val KEY_REQUEST_CODE:String = "request-code"
    const val KEY_HABIT_TYPE: String = "key-type"

    object HABIT_TYPES {
        const val BAD_HABIT = "Плохая"
        const val GOOD_HABIT = "Хорошая"
    }
}

class SharedViewModel : ViewModel() {
    val HABITS_LIST: MutableLiveData<MutableList<Habit>> = MutableLiveData(mutableListOf())
    val BAD_LIST: MutableLiveData<List<Habit>> = MutableLiveData(emptyList())
    val GOOD_LIST: MutableLiveData<List<Habit>> = MutableLiveData(emptyList())

    fun add(habit: Habit) {
        var list = HABITS_LIST.value
        if (list != null) {
            list.add(habit)
        } else {
            list = mutableListOf(habit)
        }
        HABITS_LIST.value = list!!
        update()
    }

    fun edit(position: Int, habit: Habit) {
        val list = HABITS_LIST.value
        list?.set(position, habit)
        HABITS_LIST.value = list!!
        update()
    }

    fun get(): MutableList<Habit> {
        return if (HABITS_LIST.value == null) {
            mutableListOf()
        } else {
            HABITS_LIST.value!!
        }
    }

    fun size(): Int {
        return if (HABITS_LIST.value == null) {
            0
        } else {
            HABITS_LIST.value!!.size
        }
    }

    private fun update() {
        val list = HABITS_LIST.value as List<Habit>
        BAD_LIST.value = list.filter { habit -> habit.type == Constants.HABIT_TYPES.BAD_HABIT }
        GOOD_LIST.value = list.filter { habit -> habit.type == Constants.HABIT_TYPES.GOOD_HABIT }
    }
}