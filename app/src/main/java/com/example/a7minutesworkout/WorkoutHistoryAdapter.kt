package com.example.a7minutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.HistoryItemRecyclerViewBinding

class WorkoutHistoryAdapter(private val dateAndTimeList: ArrayList<WorkoutHistoryEntity>): RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: HistoryItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        val mainLayout = binding.mainConstraintLayout
        val id = binding.idTextView
        val dateAndTime = binding.dateAndTimeTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dateAndTimeList[position]
        holder.id.text = item.id.toString()
        holder.dateAndTime.text = item.dateAndTime
        if (position % 2 == 0){
            holder.mainLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lightGray))
        }
        else{
            holder.mainLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return dateAndTimeList.size
    }
}