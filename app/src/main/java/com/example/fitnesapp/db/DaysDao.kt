package com.example.fitnesapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(dayModel: DayModel)

    @Query("SELECT * FROM day_model_table WHERE  id=:dayId")
    suspend fun getDayById(dayId: Int): DayModel

    @Query("SELECT * FROM day_model_table WHERE  difficulty=:difficulty")
    fun getAllDaysByDifficulty(difficulty: String): Flow<List<DayModel>>

    @Query("SELECT * FROM day_model_table")
    suspend fun getAllDays(): List<DayModel>

    @Delete
    suspend fun deleteDay(dayModel: DayModel)
}