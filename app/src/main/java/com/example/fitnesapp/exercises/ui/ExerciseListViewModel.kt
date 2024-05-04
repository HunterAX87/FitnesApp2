package com.example.fitnesapp.exercises.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.training.data.TrainingTopCardModel
import com.example.fitnesapp.training.utils.TrainingUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {
    val exerciseList = MutableLiveData<List<ExercisesModel>>()
    val topCardUpdate = MutableLiveData<TrainingTopCardModel>()

    fun getDayExrciseList(dayModel: DayModel?) = viewModelScope.launch {
        val day = dayModel?.id?.let {
            mainDb.daysDao.getDayById(it)
        }
        if (day != null) {
            getTopCardData(day)
        }
        val allExerciseList = mainDb.exerciseDao.getAllExercises()
        val tempExerciseList = ArrayList<ExercisesModel>()
        day?.let { dayModel ->
            dayModel.exercises.split(",").forEach { index ->
                tempExerciseList.add(allExerciseList[index.toInt()])
            }

            for (i in 0 until dayModel.doneExerciseCounter) {
                tempExerciseList[i] = tempExerciseList[i].copy(isDone = true)
            }

            exerciseList.value = tempExerciseList
        }
    }

    fun getTopCardData(dayModel: DayModel) {
        var index = 0
        when (dayModel.difficulty) {
            "middle" -> {
                index = 1
            }

            "hard" -> {
                index = 2
            }
        }
        topCardUpdate.value = TrainingUtils.topCardList[index].copy(
            progress = dayModel.doneExerciseCounter,
            maxProgress = dayModel.exercises.split(",").size
        )
    }
}