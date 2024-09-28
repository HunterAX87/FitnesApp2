package com.example.fitnesapp.custom_training.selected_exercise_list.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.SelectedExerciseListItemBinding
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.utils.Constants.CONST_1000
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class SelectedListExerciseAdapter(private val listener: Listener) :
    ListAdapter<ExercisesModel, SelectedListExerciseAdapter.ExerciseHolder>(MyComparator()) {

    class ExerciseHolder(view: View, val listener: Listener) : RecyclerView.ViewHolder(view) {
        private val binding = SelectedExerciseListItemBinding.bind(view)

        fun setData(exercise: ExercisesModel) = with(binding) {
            tvName.text = exercise.name
            tvCount.text = getTime(exercise.time)
            imEx.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            deleteItem.setOnClickListener {
                listener.onDelete(adapterPosition)
            }
        }

        private fun getTime(time: String): String {
            return if (time.startsWith("x")) {
                time
            } else {
                TimeUtils.getTime(time.toLong() * CONST_1000)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.selected_exercise_list_item, parent, false)
        return ExerciseHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class MyComparator : DiffUtil.ItemCallback<ExercisesModel>() {
        override fun areItemsTheSame(oldItem: ExercisesModel, newItem: ExercisesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExercisesModel, newItem: ExercisesModel): Boolean {
            return oldItem == newItem
        }
    }

    fun removeAt(position: Int) {
        val tempList = ArrayList(currentList)
        tempList.removeAt(position)
        submitList(tempList)
    }

    interface Listener {
        fun onDelete(pos: Int)
    }
}