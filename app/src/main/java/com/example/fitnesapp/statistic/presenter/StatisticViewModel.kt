package com.example.fitnesapp.statistic.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.example.fitnesapp.R
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.db.StatisticModel
import com.example.fitnesapp.db.WeightModel
import com.example.fitnesapp.statistic.data.DateSelectorModel
import com.example.fitnesapp.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {
    var year = -1
    var month = Calendar.getInstance().get(Calendar.MONTH)

    val eventListData = MutableLiveData<List<EventDay>>()
    val statisticData = MutableLiveData<StatisticModel>()
    val weightListData = MutableLiveData<List<WeightModel>>()
    val yearListData = MutableLiveData<List<DateSelectorModel>>()

    fun getStatisticEvents() = viewModelScope.launch {
        val eventList = ArrayList<EventDay>()
        val statisticList = mainDb.statisticDao.getStatistic()

        statisticList.forEach {
            eventList.add(
                EventDay(
                    TimeUtils.getCalendarFromDate(it.date),
                    R.drawable.star
                )
            )
        }
        eventListData.value = eventList
    }


    fun getStatisticByDate(date: String) = viewModelScope.launch {
        statisticData.value = mainDb.statisticDao
            .getStatisticByDate(date) ?: StatisticModel(
            null,
            date,
            0,
            "0"
        )
    }

    fun getYearList() = viewModelScope.launch {
        val tempYearList = ArrayList<DateSelectorModel>()
        val weightList = mainDb.weightDao.getAllWeightList()

        weightList.forEach { weightModel ->

            if (!tempYearList.any { it.text == weightModel.year.toString() }) {
                tempYearList.add(
                    DateSelectorModel(
                        weightModel.year.toString()
                    )
                )
            }
        }
        yearListData.value = tempYearList
    }

    fun getWeightByYearAndMonth() = viewModelScope.launch {
        Log.d("MyLog", "$$$$$$")
        weightListData.value = mainDb.weightDao.getMonthWeightList(
            year,
            month
        )
    }

    fun saveWeight(weight: Int) = viewModelScope.launch {
        val cv = Calendar.getInstance()
        mainDb.weightDao.insertWeight(
            WeightModel(
                null,
                weight,
                cv.get(Calendar.DAY_OF_MONTH),
                cv.get(Calendar.MONTH),
                cv.get(Calendar.YEAR)
            )
        )
    }
}