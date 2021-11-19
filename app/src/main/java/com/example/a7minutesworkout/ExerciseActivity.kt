package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private var countDownTimer: CountDownTimer? = null
    private var exercisingCountDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 10000
    private var exercisingDuration: Long = 30000
    private var restProgress = 10
    private var exercisingProgress = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarExerciseActivity)
        addBackButtonToActionBar()
        startStartingTimer()
    }

    private fun startStartingTimer() {

        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = "${millisUntilFinished/1000}"
                restProgress--
                binding.startProgressBar.progress = restProgress
            }

            override fun onFinish() {
                setUpExercisingTimer()
            }
        }.start()
    }

    private fun setUpExercisingTimer(){
        binding.startingTimer.visibility = View.INVISIBLE
        binding.exerciseTimer.visibility = View.VISIBLE
        binding.textView.text = "Exercise Name"
        if (exercisingCountDownTimer != null) {
            exercisingCountDownTimer?.cancel()
            exercisingProgress = 30
        }
        startExercisingTimer()
    }

    private fun startExercisingTimer() {
        exercisingCountDownTimer = object : CountDownTimer(exercisingDuration, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.exerciseTimerTextView.text = "${millisUntilFinished/1000}"
                exercisingProgress--
                binding.exerciseProgressBar.progress = exercisingProgress
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise Done", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun addBackButtonToActionBar() {
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarExerciseActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 10
        }
        if (exercisingCountDownTimer != null) {
            exercisingCountDownTimer?.cancel()
            exercisingProgress = 30
        }
    }

}