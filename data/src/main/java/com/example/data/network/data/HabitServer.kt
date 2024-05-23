package com.example.data.network.data

import com.example.data.db.HabitEntity
import com.example.domain.entity.Habit

data class HabitServer(
    var doneDates: List<Int>,
    var uid: String? = null,
    var frequency: Int,
    var priority: Int,
    var date: Int,
    var type: Int,
    var title: String,
    var description: String,
    var count: Int
) {


    companion object {
        fun fromHabit(habit: Habit) = HabitServer(
            count = habit.days,
            description = habit.description,
            title = habit.title,
            type = habit.type.ordinal,
            priority = habit.priority.ordinal,
            date = habit.editDate,
            frequency = habit.repeat,
            uid = habit.id,
            doneDates = habit.doneDates
        )
    }

    fun toEntity() = HabitEntity(
        title = title,
        description = description,
        priority = priority,
        days = count,
        type = type,
        editDate = date,
        id = uid!!,
        repeat = frequency,
        doneDates = doneDates
    )

}