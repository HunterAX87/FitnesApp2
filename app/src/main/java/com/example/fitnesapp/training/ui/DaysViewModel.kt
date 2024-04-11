package com.example.fitnesapp.training.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.training.data.TrainingTopCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {

    val daysList = MutableLiveData<List<DayModel>>()
    val topCardUpdate = MutableLiveData<TrainingTopCardModel>()

    fun getExerciseDaysByDifficulty(trainingTopCardModel: TrainingTopCardModel) {
        //Coroutine
        viewModelScope.launch {
            mainDb.daysDao.getAllDaysByDifficulty(trainingTopCardModel.difficulty)
                .collect { list ->
                    daysList.value = list
                    topCardUpdate.value = trainingTopCardModel.copy(
                        maxProgress = list.size,
                        progress = getProgress(list)
                    )
                }
        }
    }

    private fun getProgress(list: List<DayModel>):Int{
        var counter= 0
        list.forEach { day ->
            if(day.isDone){
                counter++
            }
        }
        return counter
    }
}