package com.example.fitnesapp.training.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentTrainingBinding
import com.example.fitnesapp.training.ui.DaysViewModel
import com.example.fitnesapp.training.ui.adapters.VpAdapter
import com.example.fitnesapp.training.utils.TrainingUtils
import com.google.android.material.tabs.TabLayoutMediator

class TrainingFragment : Fragment() {
    lateinit var binding: FragmentTrainingBinding
    private val diffLis= listOf(
        "easy",
        "middle",
        "hard"
    )
    private val model: DaysViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTrainingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vpAdapter= VpAdapter(this)
        binding.vp.adapter= vpAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp){ tab, pos ->
            tab.text=getString(TrainingUtils.tabTitles[pos])
        }.attach()
        binding.vp.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                model.getExerciseDaysByDifficulty(diffLis[position])
            }
        })
    }

}