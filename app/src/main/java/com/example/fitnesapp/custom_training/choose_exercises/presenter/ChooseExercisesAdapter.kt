package com.example.fitnesapp.custom_training.choose_exercises.presenter

import android.animation.Animator
import android.animation.Animator.AnimatorListener
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

class ChooseExercisesAdapter(private val listener: Listener) :
    ListAdapter<ExercisesModel, ChooseExercisesAdapter.ExerciseHolder>(MyComparator()) {

    class ExerciseHolder(view: View, val listener: Listener) : RecyclerView.ViewHolder(view) {
        private val binding = SelectedExerciseListItemBinding.bind(view)

        init {
            binding.animationView.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(p0: Animator) {

                }

                override fun onAnimationEnd(p0: Animator) {
                    binding.animationView.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(p0: Animator) {

                }

                override fun onAnimationRepeat(p0: Animator) {

                }

            })
        }

        fun setData(exercise: ExercisesModel) = with(binding) {
            tvName.text = exercise.name
            tvCount.text = getTime(exercise.time)
            deleteItem.visibility = View.INVISIBLE
            imEx.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            itemView.setOnClickListener {
                listener.onClick(exercise)
                binding.animationView.visibility = View.VISIBLE
                animationView.playAnimation()
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

    interface Listener {
        fun onClick(exercise: ExercisesModel)
    }
}