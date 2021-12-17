package com.example.a7minutesworkout

import android.app.Application

class WorkoutHistoryApp: Application() {

    // lazy: creating lazily means it loads the needed values to variables whenever it is needed.
    //here we are creating an instance of database. we can only have one instance of a database
    val database by lazy {
        WorkoutHistoryDatabase.getInstance(this)
    }
}