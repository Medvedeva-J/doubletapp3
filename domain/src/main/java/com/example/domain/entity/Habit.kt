package com.example.domain.entity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Habit(
    var title: String = "",
    val description: String = "",
    val priority: Priority = Priority.HARD,
    val type: HabitType = HabitType.GOOD,
    val repeat: Int = 1,
    val days: Int = 1,
    var editDate: Int = (Date().time / 1000).toInt(),
    var doneDates: List<Int> = emptyList(),
    var id: String? = null
) : Parcelable