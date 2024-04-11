package com.example.fitnesapp.exercises.ui.fragments

import android.animation.ObjectAnimator
import android.os.Build
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
import com.example.fitnesapp.fragments.WaitingFragment
import com.example.fitnesapp.getDayFromArgument
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.MainViewModel


class   ExercisesListFragment : Fragment() {
  lateinit var binding: ExercisesListFragmentBinding
  private lateinit var adapter: ExerciseAdapter
    private val model: ExerciseListViewModel by activityViewModels()
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
        topCardobserver()
        init()
        exerciseListObserver()
        dayModel= getDayFromArgument()
        model.getDayExrciseList(dayModel)

    }

    private fun init() = with(binding){
        adapter= ExerciseAdapter()
        rcView.layoutManager=LinearLayoutManager(activity)
        rcView.adapter= adapter
        bStart.setOnClickListener {
            val bundle= Bundle().apply {
                putSerializable("day", dayModel)
            }
            findNavController().navigate(
                R.id.action_exercisesListFragment_to_exercisesFragment,
                bundle
            )

        }
    }


    private fun exerciseListObserver(){
        model.exerciseList.observe(viewLifecycleOwner){list->
            adapter.submitList(list)
        }
    }

    private fun topCardobserver(){
        model.topCardUpdate.observe(viewLifecycleOwner){ card->
            binding.apply {

                val alphaAnimation= AlphaAnimation(0.8f, 1.0f)
                alphaAnimation.duration= 700
                imageView.startAnimation(alphaAnimation)
                imageView.setImageResource(card.imageId)
                difficultyTitle.setText(card.difficultyTitle)

                val alphaAnimationText= AlphaAnimation(0.0f, 1.0f)
                alphaAnimationText.startOffset=300
                alphaAnimationText.duration= 800
                difficultyTitle.setText(card.difficultyTitle)
                difficultyTitle.visibility= View.VISIBLE
                difficultyTitle.startAnimation(alphaAnimationText)

                val alphaAnimationText2= AlphaAnimation(0.0f, 1.0f)
                alphaAnimationText2.startOffset=600
                alphaAnimationText2.duration= 800
                val daysRest= card.maxProgress - card.progress
                val tvRestText= getString(R.string.rest)+ " " + daysRest
                tvRestDays.text= if (daysRest==0){
                    getString(R.string.done)
                } else {
                    tvRestText
                }
                tvRestDays.visibility= View.VISIBLE
                tvRestDays.startAnimation(alphaAnimationText2)

                pB.max=card.maxProgress
                animProgressBar(card.progress)

            }
        }
    }

    private fun animProgressBar(progress:Int){
        val anim= ObjectAnimator.ofInt(
            binding.pB,
            "progress",
            binding.pB.progress,
            progress * 100
        )
        anim.startDelay= 900
        anim.duration= 700
        anim.start()
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }
}