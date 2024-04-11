package com.example.fitnesapp.exercises.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnesapp.R
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.fragments.DayFinishFragment
import com.example.fitnesapp.getDayFromArgument
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import pl.droidsonroids.gif.GifDrawable

@AndroidEntryPoint
class ExercisesFragment : Fragment() {
    lateinit var binding: ExerciseBinding
    private val model: ExerciseViewModel by viewModels()
    private var currentDay: DayModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = getDayFromArgument()
        updataExercise()
        updataTime()
        currentDay?.let {
            model.getExercidsises(it)
        }

        binding.bNext.setOnClickListener {
            model.nextExercise()
        }
    }

    private fun updataExercise() = with(binding) {
        model.updataExercise.observe(viewLifecycleOwner) {
            imMain.setImageDrawable(GifDrawable(root.context.assets, it.image))
            tvName.text = it.name
            subTitle.text = it.subTitle
            showTime(it)
        }
    }

    private fun updataTime() = with(binding) {
        model.updataTime.observe(viewLifecycleOwner) {
            tvTime.text = TimeUtils.getTime(it)
        }
    }


    private fun showTime(exercise: ExercisesModel) {
        model.timer?.cancel()
        if (exercise.time.startsWith("x")) {
            binding.tvTime.text = exercise.time

        } else {
            model.startTimer(exercise.time.toLong())
        }
    }

    override fun onPause() {
        super.onPause()
        model.onPauseTimer()
    }
}