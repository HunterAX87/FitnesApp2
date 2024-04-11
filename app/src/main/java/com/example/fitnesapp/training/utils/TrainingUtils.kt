package com.example.fitnesapp.training.utils

import com.example.fitnesapp.R
import com.example.fitnesapp.training.data.TrainingTopCardModel

object TrainingUtils {
    const val EASY="easy"
    const val MIDDLE="middle"
    const val HARD="hard"

    val difListType= listOf(
        EASY,
        MIDDLE,
        HARD
    )

    val tabTitles= listOf(
        R.string.easy,
        R.string.middle,
        R.string.hard
    )

    val topCardList= listOf(

        TrainingTopCardModel(
            R.drawable.easy,
            R.string.easy,
            0,
            0,
            "easy"
        ),

        TrainingTopCardModel(
            R.drawable.middle,
            R.string.middle,
            0,
            0,
            "middle"
        ),

        TrainingTopCardModel(
            R.drawable.hard,
            R.string.hard,
            0,
            0,
            "hard"
        )
    )
}