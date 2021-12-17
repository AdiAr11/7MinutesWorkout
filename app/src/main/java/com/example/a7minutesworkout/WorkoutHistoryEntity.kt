package com.example.a7minutesworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout-history-table")
data class WorkoutHistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateAndTime: String = ""
    )