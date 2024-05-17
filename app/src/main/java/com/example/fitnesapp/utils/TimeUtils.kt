package com.example.fitnesapp.utils
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
object TimeUtils {

    val formatter= SimpleDateFormat("mm:ss")
    val workautFormatter= SimpleDateFormat("HH'h':mm'm':ss's'")
    val cvFormatter= SimpleDateFormat("dd/MM/yyyy")
    fun getTime(time:Long):String{
        val cv=Calendar.getInstance()
        cv.timeInMillis= time
        return formatter.format(cv.time)
    }

    fun getWorkautTime(time:Long):String{
        val cv=Calendar.getInstance()
        cv.timeInMillis= time
        return workautFormatter.format(cv.time)
    }

    fun getCurrentDate(): String{
        val cv= Calendar.getInstance()
        return cvFormatter.format(cv.time)
    }

    fun getCalendarFromDate(date: String): Calendar{
        return Calendar.getInstance().apply {
            time= cvFormatter.parse(date) as Date
        }
    }

    fun getDateFromCalendar(c : Calendar): String{
        return cvFormatter.format(c.time)
    }
}