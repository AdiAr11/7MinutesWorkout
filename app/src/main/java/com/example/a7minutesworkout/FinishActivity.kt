package com.example.a7minutesworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutHistoryDao: WorkoutHistoryDao = (application as WorkoutHistoryApp).database.workoutHistoryDao()
        addRecord(workoutHistoryDao)

        setSupportActionBar(binding.toolBarFinishActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarFinishActivity.setNavigationOnClickListener { onBackPressed() }

        binding.finishButton.setOnClickListener {
            finish()
        }
    }

    private fun addRecord(workoutHistoryDao: WorkoutHistoryDao){
        val sdf = SimpleDateFormat("dd/MMM/yyyy     hh:mm:ss", Locale.getDefault())
        val currentDateAndTime = sdf.format(Date())
        lifecycleScope.launch {
            workoutHistoryDao.insert(WorkoutHistoryEntity(dateAndTime = currentDateAndTime))
        }
    }
}