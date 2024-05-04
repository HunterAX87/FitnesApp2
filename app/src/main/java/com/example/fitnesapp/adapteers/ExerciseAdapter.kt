package com.example.fitnesapp.adapteers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.ExerciseListItemBinding
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable
class ExerciseAdapter() :
    ListAdapter<ExercisesModel, ExerciseAdapter.ExerciseHolder>(MyComparator()) {

    class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ExerciseListItemBinding.bind(view)

        fun setData(exercise: ExercisesModel) = with(binding) {
            tvName.text = exercise.name
            tvCount.text = getTime(exercise.time)
            checkBoxImage1.visibility = if (exercise.isDone) View.VISIBLE else View.INVISIBLE
            imEx.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        }

        private fun getTime(time: String): String {
            return if (time.startsWith("x")) {
                time
            } else {
                TimeUtils.getTime(time.toLong() * 1000)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseHolder(view)
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
}