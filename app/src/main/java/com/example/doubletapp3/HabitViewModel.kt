import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp3.Model

class HabitViewModel(private val model: Model, private val inputHabit: Habit) : ViewModel() {
    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()
    private val mutableIsDataLoading: MutableLiveData<Boolean?> = MutableLiveData()

    val habit: LiveData<Habit?> = mutableHabit
    val isDataLoading: LiveData<Boolean?> = mutableIsDataLoading

    init {
        load()
    }

    private fun load() {
        mutableIsDataLoading.value = true

//        model.loadDataAsync(habitId) { loadedHabit: Habit ->
//            mutableIsDataLoading.postValue(false)
//            mutableHabit.postValue(loadedHabit)
//        }
        mutableHabit.postValue(inputHabit)
    }
}