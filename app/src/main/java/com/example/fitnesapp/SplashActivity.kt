package com.example.fitnesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.databinding.ActivitySplashBinding
import com.example.fitnesapp.utils.Constants.CONST_0_0F
import com.example.fitnesapp.utils.Constants.CONST_1000L
import com.example.fitnesapp.utils.Constants.CONST_1_0F
import com.example.fitnesapp.utils.Constants.CONST_1_5F
import com.example.fitnesapp.utils.Constants.CONST_7000


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            splashAnim1.setMinProgress(CONST_0_0F)
            splashAnim1.setMaxProgress(CONST_1_0F)
            splashAnim1.repeatCount = 0
            splashAnim1.repeatMode = LottieDrawable.REVERSE
            splashAnim1.playAnimation()
            splashAnim1.speed = CONST_1_5F
        }


        timer = object : CountDownTimer(CONST_7000, CONST_1000L) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                // для плавности концовки анимации
                binding.splashAnim1.visibility = View.INVISIBLE
                // экстеншн функция
                openActivity(MainActivity::class.java)
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}

