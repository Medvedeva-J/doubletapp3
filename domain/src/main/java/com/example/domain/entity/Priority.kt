package com.example.domain.entity

enum class Priority {
    HARD,
    MEDIUM,
    LOW;

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