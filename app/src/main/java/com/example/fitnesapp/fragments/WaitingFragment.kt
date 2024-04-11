package com.example.fitnesapp.fragments
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.databinding.WaitingFragmentBinding
import com.example.fitnesapp.exercises.ui.fragments.ExercisesFragment
import com.example.fitnesapp.openFragment
import com.example.fitnesapp.utils.TimeUtils

const val COUNT_DONW_TIME= 10000L
class WaitingFragment : Fragment() {
  lateinit var binding: WaitingFragmentBinding
  private lateinit var timer: CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=WaitingFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pBar.max= COUNT_DONW_TIME.toInt()


        binding.apply {
            timerAnim.setMinProgress(0.0f)
            timerAnim.setMaxProgress(1.0f)
            timerAnim.repeatCount= ValueAnimator.INFINITE
            timerAnim.repeatMode= LottieDrawable.RESTART
            timerAnim.playAnimation()
            timerAnim.speed=0.7f
        }

        startTimer()
    }

    private fun startTimer() = with(binding){
        timer= object: CountDownTimer (COUNT_DONW_TIME,1){
            override fun onTick(restTime: Long) {
                tvText.text= TimeUtils.getTime(restTime)
                pBar.progress=restTime.toInt()
            }

            override fun onFinish() {

            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}