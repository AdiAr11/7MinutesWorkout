package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private var countDownTimer: CountDownTimer? = null
    private var exercisingCountDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 10000
    private var exercisingDuration: Long = 30000
    private var restProgress = 10
    private var exercisingProgress = 30
    private var mediaPlayer: MediaPlayer? = null
    private var adapter: ExerciseStatusAdapter? = null

    private lateinit var textToSpeech: TextToSpeech

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExerciseNumber: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarExerciseActivity)
        exerciseList = Constants.defaultExerciseList()
        textToSpeech = TextToSpeech(this, this)
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minutesworkout/${R.raw.press_start}")
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
        }catch (e: Exception){
            e.printStackTrace()
        }

        addBackButtonToActionBar()
        setUpRestTimer()
        setUpExerciseStatusRecyclerView()

    }

    private fun setUpExerciseStatusRecyclerView(){
        adapter = ExerciseStatusAdapter(exerciseList!!)
        binding.exerciseStatusRecyclerView.adapter = adapter
        binding.exerciseStatusRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpRestTimer(){
        playStartingSound()
        binding.startingTimer.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.upComingTV.visibility = View.VISIBLE
        binding.upComingExerciseTextView.visibility = View.VISIBLE
        binding.exerciseNameTextView.visibility = View.INVISIBLE
        binding.exerciseTimer.visibility = View.INVISIBLE
        binding.exerciseImageView.visibility = View.INVISIBLE

        if (currentExerciseNumber < exerciseList!!.size - 1){
            binding.upComingExerciseTextView.text = exerciseList!![currentExerciseNumber + 1].getExerciseName()
        }

        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 10
        }
        startStartingRestingTimer()
    }

    private fun playStartingSound(){
        try {
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun speakOut(textToBeSpoken: String){
        textToSpeech.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun startStartingRestingTimer() {

        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = "${millisUntilFinished/1000}"
                restProgress--
                binding.restProgressBar.progress = restProgress
            }

            override fun onFinish() {
                if (currentExerciseNumber < exerciseList?.size!! - 1) {
                    setUpExercisingTimer()
                }
            }
        }.start()
    }

    private fun setUpExercisingTimer(){
        binding.startingTimer.visibility = View.INVISIBLE
        binding.textView.visibility = View.INVISIBLE
        binding.upComingTV.visibility = View.INVISIBLE
        binding.upComingExerciseTextView.visibility = View.INVISIBLE
        binding.exerciseNameTextView.visibility = View.VISIBLE
        binding.exerciseTimer.visibility = View.VISIBLE
        binding.exerciseImageView.visibility = View.VISIBLE
        currentExerciseNumber++
        exerciseList!![currentExerciseNumber].setIsSelected(true)
        adapter?.notifyDataSetChanged()

        speakOut(" ${exerciseList!![currentExerciseNumber].getExerciseName()}")

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
                exerciseList!![currentExerciseNumber].setIsSelected(false)
                exerciseList!![currentExerciseNumber].setIsCompleted(true)
                adapter?.notifyDataSetChanged()
                if (currentExerciseNumber == exerciseList?.size!! - 1){
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
                else
                    setUpRestTimer()
            }
        }.start()
    }

    private fun addBackButtonToActionBar() {
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarExerciseActivity.setNavigationOnClickListener { customDialogForExit() }
    }
    private fun customDialogForExit(){
        val customDialogBackButton = Dialog(this)
        val exitDialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialogBackButton.setContentView(exitDialogBinding.root)
        customDialogBackButton.show()
        exitDialogBinding.yesButton.setOnClickListener {
            customDialogBackButton.dismiss()
            this@ExerciseActivity.finish()
        }
        exitDialogBinding.noButton.setOnClickListener {
            customDialogBackButton.dismiss()
        }
    }

    override fun onBackPressed() {
        customDialogForExit()
        //super.onBackPressed()
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

        if (textToSpeech != null){
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        if (mediaPlayer != null){
            mediaPlayer!!.stop()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS msg", "Language is not supported")
            }
        }
        else{
            Log.e("TTS msg", "Initialization failed, status: $status")
        }
    }

}