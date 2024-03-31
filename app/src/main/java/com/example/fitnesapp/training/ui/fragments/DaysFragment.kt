package com.example.fitnesapp.training.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.adapteers.DaysAdapter
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.training.ui.DaysViewModel
import com.example.fitnesapp.utils.DialogManager


class DaysFragment : Fragment(), DaysAdapter.Listener {
  lateinit var binding: FragmentDaysBinding
  private lateinit var adapter: DaysAdapter
  private val model: DaysViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDaysBinding.inflate(inflater, container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcVew()
//        binding.apply {
//            drawerAnim.setMinProgress(0.0f)
//            drawerAnim.setMaxProgress(1.0f)
//            drawerAnim.repeatCount= ValueAnimator.INFINITE
//            drawerAnim.repeatMode= LottieDrawable.RESTART
//            drawerAnim.playAnimation()
//            drawerAnim.speed=0.7f
//
//            drawerAnim.setOnClickListener{
//                drawer.openDrawer(GravityCompat.START)
//
//
//            }
//        back.setOnClickListener {
//            drawer.closeDrawer(GravityCompat.START)
//
//        }

//        bReset.setOnClickListener {
//            drawer.closeDrawer(GravityCompat.START)
//            DialogManager.showDialog(
//                activity as AppCompatActivity,
//                R.string.reset_days_massage,
//                object : DialogManager.Listener {
//                    override fun onClick() {
//                        model.pref?.edit()?.clear()?.apply()
//                        adapter.submitList(filldaysArray())
//                    }
//                }
//            )
//        }
    }



    private fun initRcVew()= with(binding){
        adapter= DaysAdapter(this@DaysFragment)
        rcViewDays.layoutManager= LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.itemAnimator= null
        rcViewDays.adapter= adapter

    }

    private fun updateAdapter(){
        model.daysList.observe(viewLifecycleOwner){list->
            adapter.submitList(list)
        }

    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    companion object{
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (day.isDone){
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_massage,
                object:DialogManager.Listener{
                    override fun onClick() {

                    }
                }
            )
        } else {
            openExerciseListFragment(day)
        }
    }

    private fun openExerciseListFragment(day: DayModel){
        val bundle= Bundle().apply {
            putSerializable("day", day)
        }
        findNavController().navigate(
            R.id.action_trainingFragment_to_exercisesListFragment,
            bundle
        )
    }
  }



