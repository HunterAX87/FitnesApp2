package com.example.fitnesapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Query("SELECT * From weight_table WHERE year=:year AND month=:month")
    fun getMonthWeightList(year: Int, month: Int): Flow<List<WeightModel>>

    @Query("SELECT * From weight_table WHERE year=:year AND month=:month AND day=:day")
    suspend fun getWeightToday(year: Int, month: Int, day: Int): WeightModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(weightModel: WeightModel)
}