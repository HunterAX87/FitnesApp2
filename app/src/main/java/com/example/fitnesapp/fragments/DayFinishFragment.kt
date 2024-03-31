package com.example.fitnesapp.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.databinding.DayFinishBinding
import com.example.fitnesapp.openFragment


class DayFinishFragment : Fragment() {
  lateinit var binding: DayFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DayFinishBinding.inflate(inflater, container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            congratAnim.setMinProgress(0.0f)
            congratAnim.setMaxProgress(1.0f)
            congratAnim.repeatCount= ValueAnimator.INFINITE
            congratAnim.repeatMode= LottieDrawable.RESTART
            congratAnim.playAnimation()
            congratAnim.speed=0.6f



            congratAnim2.visibility = View.VISIBLE
            congratAnim2.setMinProgress(0.0f)
            congratAnim2.setMaxProgress(1.0f)
            congratAnim2.repeatCount = ValueAnimator.INFINITE
            congratAnim2.repeatMode = LottieDrawable.RESTART
            congratAnim2.playAnimation()
            congratAnim2.speed = 0.7f

        //кейс кода для удаления первой анимации и для включения второй с помощью AnimatorListener
//            congratAnim.addAnimatorListener(object : Animator.AnimatorListener {
//                override fun onAnimationStart(animation: Animator) {
//                    // Действия при старте анимации
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
//                    congratAnim.visibility = View.INVISIBLE
//                    // Запуск второй анимации после завершения первой
//                    congratAnim2.visibility = View.VISIBLE
//                    congratAnim2.setMinProgress(0.0f)
//                    congratAnim2.setMaxProgress(1.0f)
//                    congratAnim2.repeatCount = 0
//                    congratAnim2.repeatMode = LottieDrawable.RESTART
//                    congratAnim2.playAnimation()
//                    congratAnim2.speed = 0.7f
//                }
//
//                override fun onAnimationCancel(animation: Animator) {
//                    // Действия при отмене анимации
//                }
//
//                override fun onAnimationRepeat(animation: Animator) {
//                    // Действия при повторе анимации
//                }
//            })
            bDone.setOnClickListener { openFragment(DaysFragment.newInstance()) }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }
}