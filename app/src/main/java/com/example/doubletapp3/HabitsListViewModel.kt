import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp3.Habit
import com.example.doubletapp3.HabitsDB

class HabitsListViewModel: ViewModel() {
    var habitsList = MutableLiveData<MutableList<Habit>>()
    var filteredList = MutableLiveData<List<Habit>>()
    var badList = MutableLiveData<List<Habit>>()
    var goodList = MutableLiveData<List<Habit>>()
    var nameFilter: String = ""
    var sortingFilter: Int = 1

    init {
        habitsList.value = mutableListOf()
        filteredList.value = habitsList.value
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun updateGoodList() {
        goodList.value = filteredList.value?.filter { habit -> habit.type == Constants.HABIT_TYPES.GOOD_HABIT }
    }

    fun updateBadList() {
        badList.value = filteredList.value?.filter { habit -> habit.type == Constants.HABIT_TYPES.BAD_HABIT }
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

    fun add(habit:Habit) {
        habitsList.value?.add(habit)
        update()
    }

    fun replace(position: Int, input: Habit) {
        habitsList.value?.set(position, input)
        update()
    }

    fun delete(habit: Habit) {
        habitsList.value?.remove(habit)
        update()
    }

    fun filter(inputFilter: String, sorting: Int) {
        nameFilter = inputFilter
        sortingFilter = sorting
        update()
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}