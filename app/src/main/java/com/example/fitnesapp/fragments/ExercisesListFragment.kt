package com.example.fitnesapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.adapteers.ExerciseAdapter
import com.example.fitnesapp.databinding.ExercisesListFragmentBinding
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.MainViewModel


class   ExercisesListFragment : Fragment() {
  lateinit var binding: ExercisesListFragmentBinding
  private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()
    private var dayModel:DayModel?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=ExercisesListFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayModel= getDayFromArgument()
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            for (i in 0 until model.getExerciseCount()){
                it[i]= it[i].copy(isDone = true)
            }
            adapter.submitList(it)
        }
    }

    private fun init() = with(binding){
        adapter= ExerciseAdapter()
        rcView.layoutManager=LinearLayoutManager(activity)
        rcView.adapter= adapter
        bStart.setOnClickListener {
            openFragment(WaitingFragment.newInstance())
        }
    }

    private fun getDayFromArgument(): DayModel?{
        //Для новых версий сдк используем первый способ для получения бандла, а для старый версий второй способ
        return arguments.let { bundle ->
            if (Build.VERSION.SDK_INT >= 33){
                bundle?.getSerializable("day", DayModel::class.java)
            } else{
                bundle?.getSerializable("day") as  DayModel
            }
        }
    }




    companion object {

        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }
}