package com.example.fitnesapp.exercises.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesapp.R
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.getDayFromArgument
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
        updateTvCount()
        currentDay?.let {
            model.getExerciseSizes(it)
        }

        binding.bNext.setOnClickListener {
            if (binding.bNext.text.toString() == getString(R.string.done)){
                findNavController().navigate(
                    R.id.action_exercisesFragment_to_dayFinishFragment
                )
            } else {
                model.nextExercise()
                binding.progressBar.progress = 0
            }
        }
    }

    private fun updateTvCount(){
        model.updataTvCount.observe(viewLifecycleOwner){
            binding.tvCount.text=it
        }
    }

    private fun updataExercise() = with(binding) {
        model.updataExercise.observe(viewLifecycleOwner) {
            imMain.setImageDrawable(GifDrawable(root.context.assets, it.image))
            tvName.text = it.name
            subTitle.text = it.subTitle
            setMainColors(
                subTitle.text.toString().startsWith("Start")
            )
            changeBottonText(it.name)
            showTime(it)
        }
    }

    private fun changeBottonText(title: String){
        if (title== "Nice training") binding.bNext.text= getString(R.string.done)
    }

    private fun updataTime() = with(binding) {
        model.updataTime.observe(viewLifecycleOwner) {
            tvTime.text = TimeUtils.getTime(it)
            animProgressBar(it)
        }
    }


    private fun showTime(exercise: ExercisesModel) {
        model.timer?.cancel()
        if (exercise.time.startsWith("x") || exercise.time.isEmpty()) {
            binding.tvTime.text = exercise.time

        } else {
            binding.progressBar.max= exercise.time.toInt()*1000
            model.startTimer(exercise.time.toLong())
        }
    }

    private fun setMainColors( isExercise: Boolean) = with(binding){

        val white= ContextCompat.getColor(requireContext(), R.color.white)
        val salat= ContextCompat.getColor(requireContext(), R.color.salatoviy)
        val largeSize = 34.0f
        val smallSize = 20.0f

        if(isExercise){

            tvName.setTextColor(salat)
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, largeSize)
            subTitle.setTextColor(white)
            subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, smallSize)
            tvTime.setTextColor(salat)


        } else {

            tvName.setTextColor(white)
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, smallSize)
            subTitle.setTextColor(salat)
            subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, largeSize)
            tvTime.setTextColor(white)


        }
    }

    private fun animProgressBar(restTime: Long) {
        val progressTo= if (restTime > 1000){
            restTime -1000
        } else{
            0
        }
        val anim = ObjectAnimator.ofInt(
            binding.progressBar,
            "progress",
            binding.progressBar.progress,
            progressTo.toInt()
        )
        anim.duration = 900
        anim.start()
    }

    override fun onPause() {
        super.onPause()
        model.onPauseTimer()
    }
}