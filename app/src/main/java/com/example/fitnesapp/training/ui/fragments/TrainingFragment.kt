package com.example.fitnesapp.training.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentTrainingBinding
import com.example.fitnesapp.training.ui.DaysViewModel
import com.example.fitnesapp.training.ui.adapters.VpAdapter
import com.example.fitnesapp.training.utils.TrainingUtils
import com.example.fitnesapp.utils.Constants.CONST_0_8F
import com.example.fitnesapp.utils.Constants.CONST_1000
import com.example.fitnesapp.utils.Constants.CONST_1_0F
import com.example.fitnesapp.utils.Constants.CONST_700
import com.google.android.material.tabs.TabLayoutMediator

class TrainingFragment : Fragment() {
    lateinit var binding: FragmentTrainingBinding
    private val diffLis = listOf(
        "easy",
        "middle",
        "hard"
    )
    private val model: DaysViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        topCardObserver()
        isCustomTrainingEmpty()
        model.getCustomDayList()
    }

    private fun isCustomTrainingEmpty() {
        model.isCustomListEmpty.observe(viewLifecycleOwner) {
            val index = if (it) {
                1
            } else {
                0
            }
            initVpAdapter(index)
        }
    }

    private fun initVpAdapter(index: Int) {
        val vpAdapter = VpAdapter(this, index)
        binding.vp.adapter = vpAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp) { tab, pos ->
            tab.text = getString(TrainingUtils.tabTitles[pos])
        }.attach()
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                model.getExerciseDaysByDifficulty(
                    TrainingUtils.topCardList[position]
                )
            }
        })
    }

    private fun topCardObserver() = with(binding) {
        model.topCardUpdate.observe(viewLifecycleOwner) { card ->
            val alphaAnimation = AlphaAnimation(CONST_0_8F, CONST_1_0F)
            alphaAnimation.duration = CONST_700
            imageView.setImageResource(card.imageId)
            imageView.startAnimation(alphaAnimation)
            difficultyTitle.setText(card.difficultyTitle)
            pB.max = card.maxProgress * CONST_1000
            animProgressBar(card.progress)
            val daysRestText = getString(R.string.rest) + " " + (card.maxProgress - card.progress)
            tvRestDays.text = daysRestText

        }
    }

    private fun animProgressBar(progress: Int) {
        val anim = ObjectAnimator.ofInt(
            binding.pB,
            "progress",
            binding.pB.progress,
            progress * CONST_1000
        )
        anim.duration = CONST_700
        anim.start()
    }
}