package com.example.fitnesapp.custom_training.choose_exercises.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseExerciseViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {
    val exerciseListData = MutableLiveData<List<ExercisesModel>>()
    private var dayModel: DayModel? = null

    fun getAllExercises() = viewModelScope.launch {
        exerciseListData.value = mainDb.exerciseDao.getAllExercises()
    }

    fun getDayById(id: Int) = viewModelScope.launch {
        dayModel = mainDb.daysDao.getDayById(id)
    }

    fun updateDay(exercises: String) = viewModelScope.launch {
        val oldExercises = dayModel?.exercises ?: ""
        val tempExercises = if (oldExercises.isEmpty()) {
            exercises.replaceFirst(",", "")
        } else {
            exercises
        }
        // "12,13,12,12,13,12"
        // ",12,13,12"
        dayModel?.copy(
            exercises = oldExercises + tempExercises
        )?.let {
            mainDb.daysDao.insertDay(it)
        }
    }
}