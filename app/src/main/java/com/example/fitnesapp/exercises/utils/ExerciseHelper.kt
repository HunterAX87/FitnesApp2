package com.example.fitnesapp.exercises.utils

import android.content.Context
import com.example.fitnesapp.R
import com.example.fitnesapp.db.ExercisesModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExerciseHelper @Inject constructor(val context: Context) {

    fun getExercisesOfTheDay(
        exercisesIds: String, list: List<ExercisesModel>
    ): List<ExercisesModel> {
        val exercisesIdsArray = exercisesIds.split(",")
        val tempList = ArrayList<ExercisesModel>()
        for (i in exercisesIdsArray.indices) {
            if (exercisesIdsArray[i].isNotEmpty()) {
                val exerciseId = exercisesIdsArray[i].toInt()
                val exercise = list.filter {
                    it.id == exerciseId
                }[0]
                tempList.add(exercise)
            }
        }
        return tempList
    }

    fun createExerciseStack(list: List<ExercisesModel>): List<ExercisesModel> {
        val tempList = ArrayList<ExercisesModel>()
        list.forEachIndexed { index, exercise ->

            tempList.add(
                exercise.copy(
                    time = "10",
                    subTitle = if (index == 0) {
                        context.getString(R.string.prepare_text)
                    } else {
                        context.getString(R.string.next_ex_is)
                    }
                )
            )

            tempList.add(
                exercise.copy(
                    subTitle = context.getString(R.string.start_doing)
                )
            )
        }
        tempList.add(
            ExercisesModel(
                null,
                context.getString(R.string.nice_training),
                context.getString(R.string.exercise_day_finish),
                "",
                "done.gif",
                false,
                0
            )
        )
        return tempList
    }
}
