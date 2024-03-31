package com.example.fitnesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity( tableName = "day_model_table")
data class DayModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int?= null,
    var exercises:String,
    var difficulty:String,
    var dayNumber:Int,
    var doneExerciseCounter:Int,
    var isDone:Boolean
):Serializable
