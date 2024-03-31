package com.example.fitnesapp.training.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {

    val daysList= MutableLiveData<List<DayModel>>()

    fun getExerciseDaysByDifficulty(difficulty: String){
        //Coroutine
        viewModelScope.launch {
            mainDb.daysDao.getAllDaysByDifficulty(difficulty).collect{list->
                daysList.value= list
            }
        }
    }
}