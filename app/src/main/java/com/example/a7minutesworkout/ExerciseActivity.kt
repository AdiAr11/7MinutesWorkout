package com.example.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private var countDownTimer: CountDownTimer? = null
    private var exercisingCountDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 10000
    private var exercisingDuration: Long = 30000
    private var restProgress = 10
    private var exercisingProgress = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExerciseNumber: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarExerciseActivity)
        exerciseList = Constants.defaultExerciseList()
        addBackButtonToActionBar()
        setUpRestTimer()
    }

    private fun setUpRestTimer(){
        binding.startingTimer.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.exerciseNameTextView.visibility = View.INVISIBLE
        binding.exerciseTimer.visibility = View.INVISIBLE
        binding.exerciseImageView.visibility = View.INVISIBLE

        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 10
        }
        startStartingRestingTimer()
    }

    private fun startStartingRestingTimer() {

        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = "${millisUntilFinished/1000}"
                restProgress--
                binding.restProgressBar.progress = restProgress
            }

            override fun onFinish() {
                if (currentExerciseNumber < exerciseList?.size!!) {
                    setUpExercisingTimer()
                }
            }
        }.start()
    }

    private fun setUpExercisingTimer(){
        binding.startingTimer.visibility = View.INVISIBLE
        binding.textView.visibility = View.INVISIBLE
        binding.exerciseNameTextView.visibility = View.VISIBLE
        binding.exerciseTimer.visibility = View.VISIBLE
        binding.exerciseImageView.visibility = View.VISIBLE
        currentExerciseNumber++
        binding.exerciseImageView.setImageResource(exerciseList?.get(currentExerciseNumber)!!.getImage())
        binding.exerciseNameTextView.text = exerciseList?.get(currentExerciseNumber)?.getExerciseName()
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
                setUpRestTimer()

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