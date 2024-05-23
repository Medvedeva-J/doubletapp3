package com.example.data.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(input: Date): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(input)
    }

    @TypeConverter
    fun toDate(input: String):Date {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").parse(input)
    }
}