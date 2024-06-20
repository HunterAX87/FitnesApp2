package com.example.fitnesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.fitnesapp.databinding.ActivitySplashBinding


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            splashAnim1.setMinProgress(0.0f)
            splashAnim1.setMaxProgress(1.0f)
            splashAnim1.repeatCount = 0
            splashAnim1.repeatMode = LottieDrawable.REVERSE
            splashAnim1.playAnimation()
            splashAnim1.speed = 1.5f
        }


        timer = object : CountDownTimer(7000, 1000) {
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

