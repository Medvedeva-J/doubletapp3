import android.database.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel


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

    object SORTING {
        const val NEW = 1
        const val OLD = 2
    }
}

