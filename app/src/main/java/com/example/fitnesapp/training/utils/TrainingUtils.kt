package com.example.fitnesapp.training.utils

import com.example.fitnesapp.R
import com.example.fitnesapp.training.data.TrainingTopCardModel

object TrainingUtils {
    const val EASY = "easy"
    const val MIDDLE = "middle"
    const val HARD = "hard"
    const val CUSTOM = "custom"

    val difListType = listOf(
        EASY,
        MIDDLE,
        HARD,
        CUSTOM
    )

    val tabTitles = listOf(
        R.string.easy,
        R.string.middle,
        R.string.hard,
        R.string.custom
    )

    val topCardList = listOf(

        TrainingTopCardModel(
            R.drawable.easy,
            R.string.easy,
            0,
            0,
            EASY
        ),

        TrainingTopCardModel(
            R.drawable.middle,
            R.string.middle,
            0,
            0,
            MIDDLE
        ),

        TrainingTopCardModel(
            R.drawable.hard,
            R.string.hard,
            0,
            0,
            HARD
        ),

        TrainingTopCardModel(
            R.drawable.custom1,
            R.string.custom,
            0,
            0,
            CUSTOM
        )
    )
}