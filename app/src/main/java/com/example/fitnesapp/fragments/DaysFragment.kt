package com.example.fitnesapp.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.R
import com.example.fitnesapp.adapteers.DayModel
import com.example.fitnesapp.adapteers.DaysAdapter
import com.example.fitnesapp.adapteers.ExercisesModel
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.DialogManager
import com.example.fitnesapp.utils.MainViewModel


@Suppress("UNREACHABLE_CODE")
class DaysFragment : Fragment(), DaysAdapter.Listener {
  lateinit var binding: FragmentDaysBinding
  private lateinit var adapter: DaysAdapter
  private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=FragmentDaysBinding.inflate(inflater, container,false)
        return binding.root




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay= 0
        initRcVew()
        binding.apply {
            drawerAnim.setMinProgress(0.0f)
            drawerAnim.setMaxProgress(1.0f)
            drawerAnim.repeatCount= ValueAnimator.INFINITE
            drawerAnim.repeatMode= LottieDrawable.RESTART
            drawerAnim.playAnimation()
            drawerAnim.speed=0.7f

            drawerAnim.setOnClickListener{
                drawer.openDrawer(GravityCompat.START)


            }
            back.setOnClickListener{
                drawer.closeDrawer(GravityCompat.START)

            }

            bReset.setOnClickListener {
                drawer.closeDrawer(GravityCompat.START)
                DialogManager.showDialog(
                    activity as AppCompatActivity,
                    R.string.reset_days_massage,
                    object:DialogManager.Listener{
                        override fun onClick() {
                            model.pref?.edit()?.clear()?.apply()
                            adapter.submitList(filldaysArray())
                        }
                    }
                )
            }

        }

    }


    private fun initRcVew()= with(binding){
        adapter= DaysAdapter(this@DaysFragment)
        rcViewDays.layoutManager= LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter= adapter
        adapter.submitList(filldaysArray())
    }

    private fun filldaysArray(): ArrayList<DayModel>{
        val tArray= ArrayList<DayModel>()
        var daysDoneCounter= 0
        resources.getStringArray(R.array.day_exersizes).forEach {
            model.currentDay++
            val exCounter=it.split(",").size
            tArray.add(DayModel(it, 0,model.getExerciseCount()==exCounter))
        }
        binding.pB.max=tArray.size
        tArray.forEach {
            if (it.isDone) daysDoneCounter++
        }
        updateRestCountUI(tArray.size-daysDoneCounter, tArray.size)
        return tArray
    }

    private fun updateRestCountUI(restDays:Int, days:Int)= with(binding){
        val rDays= getString(R.string.rest)+ " $restDays "+ getString(R.string.rest_days)
        tvRestDays.text= rDays
        pB.progress=days-restDays
    }

    private fun fillExerciseList(day: DayModel){
        val tempList= ArrayList<ExercisesModel>()
        day.exercises.split(",").forEach{
            val exerciseList= resources.getStringArray(R.array.exersizes)
            val exercise= exerciseList[it.toInt()]
            val exerciseArray=exercise.split("|")
            tempList.add(ExercisesModel(exerciseArray[0],exerciseArray[1], exerciseArray[2], false))
        }
        model.mutableListExercise.value= tempList

    }

    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone){
        fillExerciseList(day)
        model.currentDay= day.dayNumber
        openFragment(ExercisesListFragment.newInstance())
        }
        else{
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_massage,
                object:DialogManager.Listener{
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(), 0)

                        fillExerciseList(day)
                        model.currentDay= day.dayNumber
                        openFragment(ExercisesListFragment.newInstance())
                    }
                }
            )
        }
    }
}