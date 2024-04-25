package com.example.doubletapp3
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Habit(
    var title: String,
    val description: String,
    val priority: Int,
    val type: String,
    val repeat: Int,
    val days: Int,
    val edit_date: String,
    var position: Int? = null) : Parcelable {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
}