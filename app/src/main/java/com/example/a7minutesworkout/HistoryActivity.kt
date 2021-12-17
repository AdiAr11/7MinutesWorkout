package com.example.a7minutesworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutHistoryDao = (application as WorkoutHistoryApp).database.workoutHistoryDao()

        setSupportActionBar(binding.historyToolbar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding.historyToolbar.setNavigationOnClickListener { onBackPressed() }

        lifecycleScope.launch {
            workoutHistoryDao.fetchAllExerciseHistory().collect {
                setUpRecyclerView(it as ArrayList<WorkoutHistoryEntity>)
            }
        }

    }

    private fun setUpRecyclerView(workoutHistoryList: ArrayList<WorkoutHistoryEntity>){
        val adapter = WorkoutHistoryAdapter(workoutHistoryList)
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.historyRecyclerView.adapter = adapter
    }
}