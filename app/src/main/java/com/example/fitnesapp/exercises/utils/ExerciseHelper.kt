package com.example.fitnesapp.exercises.utils

import com.example.fitnesapp.db.ExercisesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExerciseHelper @Inject constructor() {

    fun getExercisesOfTheDay ( exerciseIndexes: String, list: List<ExercisesModel> ): List<ExercisesModel> {

        val exerciseIndexesArray= exerciseIndexes.split(",")
        val tempList= ArrayList<ExercisesModel>()

        for (i in exerciseIndexesArray.indices){
            tempList.add(
                list[exerciseIndexesArray[i].toInt()]
            )
        }
        return tempList
    }

    fun createExerciseStack(list: List<ExercisesModel>): List<ExercisesModel>{
        val tempList= ArrayList<ExercisesModel>()
        list.forEach{

            tempList.add(
                it.copy(
                    time= "10",
                    subTitle = "Relax"
                )
            )

            tempList.add(
                it.copy(

                    subTitle = "Start"
                )
            )

//            tempList.add(
//                ExercisesModel(
//                    null,
//                    "Nice training",
//                    "Exercise day finish",
//                    "",
//                    "relax1.gif",
//                    false,
//                    0
//                )
//            )
        }
        return tempList
    }

}