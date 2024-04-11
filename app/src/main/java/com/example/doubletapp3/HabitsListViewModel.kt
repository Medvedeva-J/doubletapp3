import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitsListViewModel() : ViewModel() {
    val HABITS_LIST: MutableLiveData<MutableList<Habit>> = MutableLiveData(mutableListOf())
    val FILTERED_LIST: MutableLiveData<List<Habit>> = MutableLiveData(emptyList())
    val BAD_LIST: MutableLiveData<List<Habit>> = MutableLiveData(emptyList())
    val GOOD_LIST: MutableLiveData<List<Habit>> = MutableLiveData(emptyList())
    var NAME_FILTER: String = ""
    var DATA_SORT: Int = 1

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

    fun getHabit(ind: Int): Habit? {
        return if (HABITS_LIST.value == null) {
            HABITS_LIST.value?.get(ind)
        } else {
            null
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
        var filtered = list.filter { habit -> habit.title.contains(NAME_FILTER) }.sortedBy { habit -> habit.edit_date }
        when (DATA_SORT) {
            Constants.SORTING.NEW -> {
                filtered = filtered.reversed()
            }
        }
        FILTERED_LIST.value = filtered
        BAD_LIST.value = FILTERED_LIST.value?.filter { habit -> habit.type == Constants.HABIT_TYPES.BAD_HABIT }
        GOOD_LIST.value = FILTERED_LIST.value?.filter { habit -> habit.type == Constants.HABIT_TYPES.GOOD_HABIT }
    }

    fun filter(name: String, sorting: Int) {
        NAME_FILTER = name
        DATA_SORT = sorting
        update()
    }
}