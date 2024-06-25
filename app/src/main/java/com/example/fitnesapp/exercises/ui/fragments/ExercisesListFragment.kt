package com.example.fitnesapp.exercises.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.adapteers.ExerciseAdapter
import com.example.fitnesapp.databinding.ExercisesListFragmentBinding
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.exercises.ui.ExerciseListViewModel
import com.example.fitnesapp.getDayFromArgument
import com.example.fitnesapp.utils.Constants.CONST_0_0F
import com.example.fitnesapp.utils.Constants.CONST_0_8F
import com.example.fitnesapp.utils.Constants.CONST_1000
import com.example.fitnesapp.utils.Constants.CONST_1_0F
import com.example.fitnesapp.utils.Constants.CONST_300
import com.example.fitnesapp.utils.Constants.CONST_600
import com.example.fitnesapp.utils.Constants.CONST_700
import com.example.fitnesapp.utils.Constants.CONST_800
import com.example.fitnesapp.utils.Constants.CONST_900


class ExercisesListFragment : Fragment() {
    lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExerciseAdapter
    private val model: ExerciseListViewModel by activityViewModels()
    private var dayModel: DayModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ExercisesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayModel = getDayFromArgument()
        topCardobserver()
        init()
        exerciseListObserver()
        model.getDayExerciseList(dayModel)

    }

    private fun init() = with(binding) {
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("day", dayModel)
            }
            findNavController().navigate(
                R.id.action_exercisesListFragment_to_waitingFragment,
                bundle
            )

        }
    }


    private fun exerciseListObserver() {
        model.exerciseList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun topCardobserver() {
        model.topCardUpdate.observe(viewLifecycleOwner) { card ->
            binding.apply {

                val alphaAnimation = AlphaAnimation(CONST_0_8F, CONST_1_0F)
                alphaAnimation.duration = CONST_700
                imageView.startAnimation(alphaAnimation)
                imageView.setImageResource(card.imageId)
                difficultyTitle.setText(card.difficultyTitle)

                val alphaAnimationText = AlphaAnimation(CONST_0_0F, CONST_1_0F)
                alphaAnimationText.startOffset = CONST_300
                alphaAnimationText.duration = CONST_800
                difficultyTitle.setText(card.difficultyTitle)
                difficultyTitle.visibility = View.VISIBLE
                difficultyTitle.startAnimation(alphaAnimationText)

                val alphaAnimationText2 = AlphaAnimation(CONST_0_0F, CONST_1_0F)
                alphaAnimationText2.startOffset = CONST_600
                alphaAnimationText2.duration = CONST_800
                val daysRest = card.maxProgress - card.progress
                val tvRestText = getString(R.string.rest) + " " + daysRest
                tvRestDays.text = if (daysRest == 0) {
                    getString(R.string.done)
                } else {
                    tvRestText
                }
                tvRestDays.visibility = View.VISIBLE
                tvRestDays.startAnimation(alphaAnimationText2)

                pB.max = card.maxProgress * CONST_1000
                animProgressBar(card.progress)

            }
        }
    }

    private fun animProgressBar(progress: Int) {
        val anim = ObjectAnimator.ofInt(
            binding.pB,
            "progress",
            binding.pB.progress,
            progress * CONST_1000
        )
        anim.startDelay = CONST_900
        anim.duration = CONST_700
        anim.start()
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }
}