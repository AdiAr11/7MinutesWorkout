package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startFrameLayout.setOnClickListener {
            val exerciseStartIntent = Intent(this, ExerciseActivity::class.java)
            startActivity(exerciseStartIntent)
        }

        binding.bmiCalculatorFrameLayout.setOnClickListener {
            val bmiCalculatorIntent = Intent(this, BMIcalculatorActivity::class.java)
            startActivity(bmiCalculatorIntent)
        }

    }

    override fun onNightModeChanged(mode: Int) {
        super.onNightModeChanged(mode)
    }
}