package com.example.fitnesapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StatisticDao {

    @Query("SELECT * FROM statistic_table")
    suspend fun getStatistic(): List<StatisticModel>

    @Query("SELECT * FROM statistic_table WHERE date =:date")
    suspend fun getStatisticByDate(date: String): StatisticModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayStatistic(statisticModel: StatisticModel)

    @Query("DELETE FROM statistic_table")
    suspend fun clearStatistic()
}