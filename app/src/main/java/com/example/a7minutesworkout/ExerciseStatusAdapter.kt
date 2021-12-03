package com.example.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val itemsList: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root) {
        val itemTV = binding.itemTextVIew
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseModel = itemsList[position]
        holder.itemTV.text = exerciseModel.getId().toString()

        when{
            exerciseModel.getIsSelected() -> {
                holder.itemTV.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_thin_accent_color_border)
                holder.itemTV.setTextColor(Color.parseColor("#212121"))
            }
            exerciseModel.getIsCompleted() -> {
                holder.itemTV.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_background
                )
                holder.itemTV.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.itemTV.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_gray_color_background)
                holder.itemTV.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}