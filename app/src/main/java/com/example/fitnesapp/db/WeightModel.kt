package com.example.fitnesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weight_table")
data class WeightModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var weight: Int,
    var day: Int,
    var month: Int,
    var year: Int
)