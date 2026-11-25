package com.nyp.scaffoldingapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Lists are saved as one string joined with "|" (see ExerciseMappers.kt)
@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val level: String,
    val equipment: String,
    val primaryMuscles: String,
    val secondaryMuscles: String,
    val suggested: String,
    val steps: String,
    val videoUrl: String
)