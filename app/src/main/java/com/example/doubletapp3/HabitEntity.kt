package com.example.doubletapp3

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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
    var edit_date: Int,
    var doneDates: List<Int>
) {
    companion object {
        fun fromHabit(habit: Habit): HabitEntity {
            return HabitEntity(
                id = habit.id!!,
                edit_date = habit.edit_date,
                days = habit.days,
                repeat = habit.repeat,
                type = habit.type.ordinal,
                priority = habit.priority.ordinal,
                description = habit.description,
                title = habit.title,
                doneDates = habit.doneDates
            )
        }
    }

    fun toHabit() = Habit (
        title = title,
        description = description,
        priority = Priority.createByPriority(priority),
        days = days,
        type = HabitType.createByType(type),
        edit_date = edit_date,
        id = id,
        repeat = repeat,
        doneDates = doneDates
    )
}