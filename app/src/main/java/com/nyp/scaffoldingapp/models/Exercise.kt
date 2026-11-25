package com.nyp.scaffoldingapp.models

data class Exercise(
    val id: Int,
    val name: String,
    val category: String,
    val level: String,
    val equipment: String,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val suggested: String,
    val steps: List<String>,
    val videoUrl: String
)