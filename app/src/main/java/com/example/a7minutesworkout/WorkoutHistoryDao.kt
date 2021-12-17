package com.example.a7minutesworkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutHistoryDao {

    @Insert
    suspend fun insert(workoutHistoryEntity: WorkoutHistoryEntity)

    @Query("SELECT * FROM `workout-history-table`")
    fun fetchAllExerciseHistory(): Flow<List<WorkoutHistoryEntity>>

}