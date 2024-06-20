package com.example.fitnesapp.custom_training.selected_exercise_list.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.exercises.utils.ExerciseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedExercisesListViewModel @Inject constructor(
    private val mainDb: MainDb,
    private val exerciseHelper: ExerciseHelper
) : ViewModel() {
    private var dayModel: DayModel? = null
    val listExercisesData = MutableLiveData<List<com.example.fitnesapp.db.ExercisesModel>>()

    fun getExercises(id: Int) = viewModelScope.launch {
        dayModel = mainDb.daysDao.getDayById(id)
        val exerciseList = mainDb.exerciseDao.getAllExercises()
        listExercisesData.value = exerciseHelper
            .getExercisesOfTheDay(
                dayModel?.exercises!!,
                exerciseList
            )
    }

    fun updateDay(exercises: String) = viewModelScope.launch {
        // ",12,12,13,13"
        val tempExercises = exercises.replaceFirst(",", "")
        // "12,12,13,13"
        mainDb.daysDao.insertDay(
            dayModel?.copy(
                doneExerciseCounter = 0,
                isDone = false,
                exercises = tempExercises
            )!!
        )
    }
}