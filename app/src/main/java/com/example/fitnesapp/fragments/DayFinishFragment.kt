package com.example.fitnesapp.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DayFinishBinding
import com.example.fitnesapp.utils.Constants.CONST_0_0F
import com.example.fitnesapp.utils.Constants.CONST_0_6F
import com.example.fitnesapp.utils.Constants.CONST_0_7F
import com.example.fitnesapp.utils.Constants.CONST_1_0F


class DayFinishFragment : Fragment() {
    lateinit var binding: DayFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            congratAnim.setMinProgress(CONST_0_0F)
            congratAnim.setMaxProgress(CONST_1_0F)
            congratAnim.repeatCount = ValueAnimator.INFINITE
            congratAnim.repeatMode = LottieDrawable.RESTART
            congratAnim.playAnimation()
            congratAnim.speed = CONST_0_6F



            congratAnim2.visibility = View.VISIBLE
            congratAnim2.setMinProgress(CONST_0_0F)
            congratAnim2.setMaxProgress(CONST_1_0F)
            congratAnim2.repeatCount = ValueAnimator.INFINITE
            congratAnim2.repeatMode = LottieDrawable.RESTART
            congratAnim2.playAnimation()
            congratAnim2.speed = CONST_0_7F

            bDone.setOnClickListener {
                findNavController().popBackStack(
                    R.id.trainingFragment,
                    inclusive = false
                )
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(
                R.id.trainingFragment,
                inclusive = false
            )
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }
}