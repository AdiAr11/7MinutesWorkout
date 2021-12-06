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

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.usRadioButton){
                binding.heightTextInputLayout.visibility = View.INVISIBLE
                binding.weightTextInputLayout.visibility = View.INVISIBLE
                binding.weightUSPoundTextInputLayout.visibility = View.VISIBLE
                binding.heightUSFeetTextInputLayout.visibility = View.VISIBLE
                binding.heightUSInchTextInputLayout.visibility = View.VISIBLE
                clearValues()
            }
            else if(checkedId == R.id.metricRadioButton){
                binding.heightTextInputLayout.visibility = View.VISIBLE
                binding.heightUSFeetTextInputLayout.visibility = View.INVISIBLE
                binding.heightUSInchTextInputLayout.visibility = View.INVISIBLE
                binding.weightTextInputLayout.visibility = View.VISIBLE
                binding.weightUSPoundTextInputLayout.visibility = View.INVISIBLE
                clearValues()
            }
        }
        binding.bmiCalculateButton.setOnClickListener {
            if (areUSvaluesAdded() && binding.radioGroup.checkedRadioButtonId == R.id.usRadioButton)
                calculateUSunitBMI()

            else if (areMetricValuesAdded() && binding.radioGroup.checkedRadioButtonId == R.id.metricRadioButton)
                    calculateMetricBMI()

            else{
                Toast.makeText(this, "Please enter all the values", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearValues(){
        binding.weightEditText.text?.clear()
        binding.weightUSPoundEditText.text?.clear()
        binding.heightEditText.text?.clear()
        binding.heightUSFeetEditText.text?.clear()
        binding.heightUSInchEditText.text?.clear()
        binding.textView.text = ""
        binding.bmiTextView.text = ""
        binding.messageTextView.text = ""
        binding.remarkTextView.text = ""
    }

    private fun areMetricValuesAdded(): Boolean{
        return !(binding.weightEditText.text.isNullOrEmpty() || binding.heightEditText.text.isNullOrEmpty())
    }
    private fun areUSvaluesAdded(): Boolean{
        return !(binding.weightUSPoundEditText.text.isNullOrEmpty() || binding.heightUSFeetEditText.text.isNullOrEmpty()
                || binding.heightUSInchEditText.text.isNullOrEmpty())
    }

    private fun calculateMetricBMI(){
        val height = binding.heightEditText.text.toString().toFloat() / 100
        val weight = binding.weightEditText.text.toString().toFloat()
        val bmi = weight/(height * height)
        printBMIandMessages(bmi)
    }

    private fun calculateUSunitBMI(){
        val weight = binding.weightUSPoundEditText.text.toString().toFloat()
        val height = binding.heightUSFeetEditText.text.toString().toFloat() * 12 + binding.heightUSInchEditText.text.toString().toFloat()
        val bmi = (weight / (height * height)) * 703
        printBMIandMessages(bmi)
    }

    private fun printBMIandMessages(bmi: Float){

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