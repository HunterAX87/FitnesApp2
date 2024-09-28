package com.example.fitnesapp.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.fitnesapp.R
import com.example.fitnesapp.utils.Constants.CONST_1
import com.example.fitnesapp.utils.Constants.CONST_1_0F

class MySoundPool(private val context: Context) {
    private var soundPool: SoundPool? = null
    private var finishSoundId = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1)
            .build()

        loadSound()
    }

    private fun loadSound() {
        finishSoundId = soundPool?.load(context, R.raw.finish_exercise, 1) ?: 0
    }

    fun playSound() {
        soundPool?.play(
            finishSoundId,
            CONST_1_0F,
            CONST_1_0F,
            CONST_1,
            0,
            CONST_1_0F
        )
    }
}