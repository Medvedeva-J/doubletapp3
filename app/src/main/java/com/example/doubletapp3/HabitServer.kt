package com.example.doubletapp3

data class HabitServer(
    var done_dates: List<Int>,
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
            date = habit.edit_date,
            frequency = habit.repeat,
            uid = habit.id,
            done_dates = habit.doneDates
        )
    }

    fun toEntity() = HabitEntity(
        title = title,
        description = description,
        priority = priority,
        days = count,
        type = type,
        edit_date = date,
        id = uid!!,
        repeat = frequency,
        doneDates = done_dates
    )

}