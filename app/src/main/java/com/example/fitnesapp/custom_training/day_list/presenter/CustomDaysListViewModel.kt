package com.example.fitnesapp.custom_training.day_list.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomDaysListViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {

    val daysListData = mainDb.daysDao.getAllDaysByDifficulty("custom").asLiveData(Dispatchers.Main)

    fun insertDay(dayModel: DayModel) = viewModelScope.launch {
        mainDb.daysDao.insertDay(dayModel)
    }

    fun deleteDay(day: DayModel) = viewModelScope.launch {
        mainDb.daysDao.deleteDay(day)
    }
}