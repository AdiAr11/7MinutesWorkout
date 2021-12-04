package com.example.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIcalculatorActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBmicalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bmiToolbar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding.bmiToolbar.setNavigationOnClickListener { onBackPressed() }

        binding.bmiCalculateButton.setOnClickListener {
            if (areValuesAdded())
                calculateBMI()
            else{
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun areValuesAdded(): Boolean{
        return !(binding.weightEditText.text.isNullOrEmpty() || binding.heightEditText.text.isNullOrEmpty())
    }

    private fun calculateBMI(){
        val weight = binding.weightEditText.text.toString().toFloat()
        val height = binding.heightEditText.text.toString().toFloat() / 100
        val bmi = weight/(height * height)
        binding.bmiTextView.visibility = View.VISIBLE
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(3, RoundingMode.HALF_EVEN).toString()
        binding.bmiTextView.text = bmiValue
        var msg: String? = null
        var description: String? = null
        when{
            bmi < 15.0 -> {
                msg = "Severely Underweight"
                description= "Oops! You need to take care of yourself. Maybe take nutritious diet"
            }
            bmi in 15.0 .. 18.5 ->{
                msg = "Underweight"
                description = "Oops! You need to take care of yourself. Maybe take nutritious diet"
            }
            bmi in 18.5 .. 24.9 ->{
                msg = "Normal"
                description = "Congratulations! You are healthy"
            }
            bmi in 24.9 .. 35.0 -> {
                msg = "Overweight"
                description = "Oops! You need to take care of yourself. Maybe workout more"
            }
            bmi > 35.0 -> {
                msg = "Severely Overweight"
                description = "Oops! You need to take care of yourself. Maybe workout more"
            }
        }
        binding.messageTextView.text = msg
        binding.remarkTextView.text = description
    }
}