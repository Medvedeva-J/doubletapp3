package com.example.domain.entity

enum class HabitType {
    GOOD,
    BAD;

    companion object {
        fun createByType(ordinal: Int): HabitType {
            return when (ordinal) {
                1 -> BAD
                else -> GOOD
            }
        }
    }
}