package com.example.fitnesapp.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitnesapp.R
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.MainViewModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExercisesFragment : Fragment() {
    lateinit var binding: ExerciseBinding
    private val model: MainViewModel by activityViewModels()
    private var exerciseCounter=0
    private var exList: ArrayList<ExercisesModel>? = null
    private var timer: CountDownTimer?= null
    private var currentDay=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=ExerciseBinding.inflate(inflater, container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay= model.currentDay
        exerciseCounter=model.getExerciseCount()
        model.mutableListExercise.observe(viewLifecycleOwner){
            exList= it
            nextExercise()
        }

        binding.bNext.setOnClickListener {
            nextExercise()
            binding.progressBar.progress=0
        }
    }


    private fun nextExercise(){
        if (exerciseCounter < exList?.size!!){
            val ex= exList?.get(exerciseCounter++)?: return

            showExercise(ex)
            shoowNextExercise()
            setExerciseType(ex)
        } else {
            exerciseCounter++
            openFragment(DayFinishFragment.newInstance())
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showExercise(exercise: ExercisesModel)= with(binding){

        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        tvName.text=exercise.name
        tvCount.text= "$exerciseCounter / ${exList?.size}"

    }

    private fun setExerciseType(exercise: ExercisesModel){
        timer?.cancel()
        if(exercise.time.startsWith("x")){
            binding.tvTime.text = exercise.time

        } else {
            startTimer(exercise)
        }
    }

    private fun shoowNextExercise()= with(binding){
        if (exerciseCounter < exList?.size!!){

            val ex= exList?.get(exerciseCounter)?: return
            imNext.setImageDrawable(GifDrawable(root.context.assets, ex.image))
            setTimeType(ex)
        } else {
            imNext.setImageDrawable(GifDrawable(root.context.assets, "done.gif"))
            tvNextName.text=getString(R.string.finish)
            bNext.text=getString(R.string.complete)
        }
    }

    private fun setTimeType(ex: ExercisesModel){
        if(ex.time.startsWith("x")){
            binding.tvNextName.text = ex.name + ": ${ex.time}"
        } else {
            val name = ex.name + ": ${TimeUtils.getTime(ex.time.toLong() * 1000)}"
            binding.tvNextName.text = name
        }
    }


    private fun startTimer(exercise: ExercisesModel) = with(binding) {
        //timer?.cancel()
        progressBar.max = exercise.time.toInt() * 1000
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1){
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                progressBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }



    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(), exerciseCounter-1)
        timer?.cancel()
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExercisesFragment()
    }
}