package com.example.fitnesapp.db

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_table")
    suspend fun getAllExercises(): List<ExercisesModel>
}