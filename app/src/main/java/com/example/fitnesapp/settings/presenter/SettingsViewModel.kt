package com.example.fitnesapp.settings.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {
    private lateinit var dayList: ArrayList<DayModel>

    fun clearAllData() = viewModelScope.launch {
        dayList = mainDb.daysDao.getAllDays() as ArrayList<DayModel>
        dayList.forEach {
            mainDb.daysDao.insertDay(
                it.copy(
                    doneExerciseCounter = 0,
                    isDone = false
                )
            )
        }

        clearWeight()
        mainDb.statisticDao.clearStatistic()
    }

    fun clearProgress() = viewModelScope.launch {
        dayList = mainDb.daysDao.getAllDays() as ArrayList<DayModel>
        dayList.forEach {
            mainDb.daysDao.insertDay(
                it.copy(
                    doneExerciseCounter = 0,
                    isDone = false
                )
            )
        }
    }

    fun clearWeight() = viewModelScope.launch {
        mainDb.weightDao.clearWeight()
    }

    fun clearStatistic() = viewModelScope.launch {
        mainDb.statisticDao.clearStatistic()
    }
}