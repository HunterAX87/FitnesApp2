package com.example.fitnesapp.exercises.ui.fragments

import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.exercises.utils.ExerciseHelper
import com.example.fitnesapp.utils.MySoundPool
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val mainDb: MainDb,
    private val exerciseHelper: ExerciseHelper,
    private val tts: TextToSpeech,
    private val soundPool: MySoundPool
) : ViewModel() {
    var updataExercise = MutableLiveData<ExercisesModel>()
    var updataTime = MutableLiveData<Long>()
    var updataTvCount = MutableLiveData<String>()
    private var currentDay: DayModel? = null
    private var exerciseStack: List<ExercisesModel> = emptyList()
    private var doneExerciseCounter = 0
    private var doneExerciseCounterToSave = 0
    private var totalExersiseNumber = 0
    var timer: CountDownTimer? = null

    private fun updateDay(dayModel: DayModel) = viewModelScope.launch {
        mainDb.daysDao.insertDay(dayModel)
    }

    private fun isDayDone() {
        if (totalExersiseNumber == doneExerciseCounterToSave - 1) {
            currentDay = currentDay?.copy(isDone = true)
            currentDay?.let {
                updateDay(it)
            }
        }
    }


    fun getExerciseSizes(day: DayModel) = viewModelScope.launch {
        currentDay = day.id?.let { mainDb.daysDao.getDayById(it) }

        val exerciseList = mainDb.exerciseDao.getAllExercises()
        val exercisesOfTheDay = exerciseHelper.getExercisesOfTheDay(day.exercises, exerciseList)

        doneExerciseCounterToSave = currentDay?.doneExerciseCounter ?: 0
        totalExersiseNumber = day.exercises.split(",").size

        exerciseStack = exerciseHelper.createExerciseStack(
            exercisesOfTheDay.subList(
                currentDay?.doneExerciseCounter ?: 0,
                exercisesOfTheDay.size
            )
        )
        nextExercise()

    }

    private fun updateTvCount() {
        if (doneExerciseCounter % 2 == 0) {
            val text = "${doneExerciseCounterToSave++}/$totalExersiseNumber"
            updataTvCount.value = text
        }
    }

    fun nextExercise() {
        timer?.cancel()
        updateTvCount()
        val exercise = exerciseStack[doneExerciseCounter++]
        speechExercise(exercise)
        updataExercise.value = exercise
    }

    fun startTimer(time: Long) {

        timer = object : CountDownTimer((time + 1) * 1000, 1000) {
            override fun onTick(restTime: Long) {
                updataTime.value = restTime
                speechLastDigits(restTime)
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }


    private fun speechLastDigits(time: Long) {
        if (time <= 0) return
        val timeInSeconds = (time / 1000).toInt()

        if (timeInSeconds == 0) {
            soundPool.playSound()
            return
        }

        if (timeInSeconds < 4) {
            speechText(timeInSeconds.toString())
        }
    }

    private fun speechExercise(exercisesModel: ExercisesModel) {
        if (exercisesModel.subTitle.startsWith("Relax")) {
            speechText(
                "${exercisesModel.subTitle}. ${exercisesModel.name}"
            )
        } else {
            speechText(
                "${exercisesModel.subTitle}. ${exercisesModel.name}.   " +
                        getTimeToSpeech(exercisesModel)
            )
        }
    }

    private fun getTimeToSpeech(exercisesModel: ExercisesModel): String {
        return if (exercisesModel.time.startsWith("x")) {
            "${exercisesModel.time.replace("x", " ")} times"
        } else {
            if (!exercisesModel.time.startsWith("")) {
                "${exercisesModel.time} seconds"
            } else{
                ""
            }
        }
    }

    private fun speechText(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ud_id")
    }

    fun onPauseTimer() {
        timer?.cancel()
        tts.stop()
        isDayDone()
        updateDay(
            currentDay!!.copy(
                doneExerciseCounter = if (doneExerciseCounterToSave > 0) {
                    doneExerciseCounterToSave - 1
                } else {
                    0
                }
            )
        )
    }
}