package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.entity.HabitType
import com.example.domain.entity.Priority
import com.example.domain.entity.Habit

@Entity
@TypeConverters(HabitTypeConverter::class)
data class HabitEntity (
    @PrimaryKey var id: String,
    var title: String,
    var description: String,
    var priority: Int,
    var type: Int,
    var repeat: Int,
    var days: Int,
    var editDate: Int,
    var doneDates: List<Int>
) {
    companion object {
        fun Habit.fromHabitToEntity(): HabitEntity {
            return HabitEntity(
                id = id!!,
                editDate = editDate,
                days = days,
                repeat = repeat,
                type = type.ordinal,
                priority = priority.ordinal,
                description = description,
                title = title,
                doneDates = doneDates
            )
        }
    }

    fun toHabit() = Habit (
        title = title,
        description = description,
        priority = Priority.createByPriority(priority),
        days = days,
        type = HabitType.createByType(type),
        editDate = editDate,
        id = id,
        repeat = repeat,
        doneDates = doneDates
    )
}