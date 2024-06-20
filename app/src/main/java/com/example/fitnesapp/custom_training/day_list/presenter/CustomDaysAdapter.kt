package com.example.fitnesapp.custom_training.day_list.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.CustomDaysListItemBinding
import com.example.fitnesapp.db.DayModel

class CustomDaysAdapter(var listener: Listener) :
    ListAdapter<DayModel, CustomDaysAdapter.DayHolder>(MyComparator()) {

    class DayHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CustomDaysListItemBinding.bind(view)

        fun setData(day: DayModel, listener: Listener) = with(binding) {
            val name = root.context.getString(R.string.day) + " ${adapterPosition + 1}"
            tvName.text = name
            val exCounter = getExerciseProgress(day, root.context)
            tvExCounter.setTextColor(getExerciseCounterTextColor(day, root.context))
            tvExCounter.text = exCounter
            itemView.setOnClickListener {
                listener.onClick(day.copy(dayNumber = adapterPosition + 1))
            }
            delete.setOnClickListener {
                listener.onDelete(day)
            }
        }

        private fun getExerciseCounterTextColor(day: DayModel, context: Context): Int {
            return if (day.isDone) {
                ContextCompat.getColor(context, R.color.salatoviy)
            } else {
                ContextCompat.getColor(context, R.color.blue)
            }
        }

        private fun getExerciseProgress(day: DayModel, context: Context): String {
            if (day.exercises.isEmpty()) return "0 ${context.getString(R.string.exercises)}"
            val totalExercises = day.exercises.split(",").size
            return if (day.isDone) {
                context.getString(R.string.done)
            } else {
                "$totalExercises " +
                        context.getString(R.string.exercises) +
                        " | ${context.getString(R.string.progress)} " +
                        ((day.doneExerciseCounter * 100) / totalExercises) +
                        "%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_days_list_item, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class MyComparator : DiffUtil.ItemCallback<DayModel>() {
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }
    }

    fun removeAt(position: Int) {
        val tempList = ArrayList(currentList)
        tempList.removeAt(position)
        submitList(tempList)
    }

    interface Listener {
        fun onClick(day: DayModel)
        fun onDelete(day: DayModel)
    }
}