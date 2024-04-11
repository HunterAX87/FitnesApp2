package com.example.fitnesapp.db
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "statistic_table")
data class StatisticModel (
    @PrimaryKey(autoGenerate = true)
    var id:Int?= null,
    var date:String,
    var kcal:Int,
    var workautTime:String
)