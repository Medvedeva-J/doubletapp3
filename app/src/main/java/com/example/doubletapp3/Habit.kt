package com.example.doubletapp3
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    var title: String,
    val description: String,
    val priority: Priority,
    val type: HabitType,
    val repeat: Int,
    val days: Int,
    val edit_date: Int,
    var doneDates: List<Int>,
    var id: String?
) : Parcelable {
}

enum class Priority(val id: Int){
    HARD(R.string.hardText),
    MEDIUM(R.string.mediumText),
    LOW(R.string.lowText);

    companion object {
        fun createByPriority(ordinal: Int): Priority {
            return when (ordinal) {
                1 -> MEDIUM
                2 -> LOW
                else -> HARD
            }
        }
    }
}

enum class HabitType(val id: Int){
    GOOD(R.string.goodText),
    BAD(R.string.badText);

    companion object {
        fun createByType(ordinal: Int): HabitType {
            return when (ordinal) {
                1 -> BAD
                else -> GOOD
            }
        }
    }
}