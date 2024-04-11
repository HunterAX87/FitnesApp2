package com.example.fitnesapp.exercises.ui.fragments

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.exercises.utils.ExerciseHelper
import com.example.fitnesapp.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val mainDb: MainDb,
    private val exerciseHelper: ExerciseHelper
): ViewModel() {
    var updataExercise= MutableLiveData<ExercisesModel>()
    var updataTime= MutableLiveData<Long>()
    var currentDay: DayModel? = null
    private  var exerciseStack: List<ExercisesModel> = emptyList()
    private var doneExerciseCounter= 0
    var timer: CountDownTimer?= null

    private fun updateDay(dayModel: DayModel)= viewModelScope.launch {
        mainDb.daysDao.insertDay(dayModel)
    }

    private fun dayDone(){
        currentDay= currentDay?.copy(isDone = true)
        currentDay?.let {
            updateDay(it)
        }
    }


     fun getExercidsises(day:DayModel){
        currentDay= day
        viewModelScope.launch {
            val exerciseList =mainDb.exerciseDao.getAllExercises()
            val exercisesOfTheDay= exerciseHelper.getExercisesOfTheDay(day.exercises, exerciseList)
            exerciseStack= exerciseHelper.createExerciseStack(
                exercisesOfTheDay.subList(day.doneExerciseCounter, exercisesOfTheDay.size)
            )
            nextExercise()
        }
    }

    fun nextExercise(){
        val exercise= exerciseStack[doneExerciseCounter++]
        updataExercise.value=exercise
    }

    fun startTimer(time:Long){
        timer?.cancel()
        timer = object : CountDownTimer((time+1) * 1000, 1){
            override fun onTick(restTime: Long) {
                updataTime.value=restTime
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }

    fun onPauseTimer(){
        timer?.cancel()
    }
}