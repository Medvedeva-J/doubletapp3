import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp3.Habit

class HabitViewModel(private val inputHabit: Habit?) : ViewModel() {
    val habit = MutableLiveData<Habit>()

    init {
        load()
    }

    private fun load() {

//        model.loadDataAsync(habitId) { loadedHabit: Habit ->
//            mutableIsDataLoading.postValue(false)
//            mutableHabit.postValue(loadedHabit)
//        }
    }

    fun set(input: Habit) {
        habit.value = input
    }
}