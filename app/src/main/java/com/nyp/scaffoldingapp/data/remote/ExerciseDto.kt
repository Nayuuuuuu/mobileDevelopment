package com.nyp.scaffoldingapp.data.remote

// Everything is nullable because JSON from the internet can be missing fields
data class ExerciseDto(
    val id: Int?,
    val name: String?,
    val category: String?,
    val level: String?,
    val equipment: String?,
    val primaryMuscles: List<String>?,
    val secondaryMuscles: List<String>?,
    val suggested: String?,
    val steps: List<String>?,
    val videoUrl: String?
)