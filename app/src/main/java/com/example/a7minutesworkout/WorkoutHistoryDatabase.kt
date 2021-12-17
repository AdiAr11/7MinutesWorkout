package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorkoutHistoryEntity::class], version = 1)
abstract class WorkoutHistoryDatabase: RoomDatabase() {

    abstract fun workoutHistoryDao(): WorkoutHistoryDao

    companion object{

        //Marks the JVM backing field of the annotated property as volatile,
        // meaning that writes to this field are immediately made visible to other threads.
        @Volatile
        private var INSTANCE: WorkoutHistoryDatabase? = null

        fun getInstance(context: Context): WorkoutHistoryDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, WorkoutHistoryDatabase::class.java, "workout_history_database").
                            fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}