package com.nyp.scaffoldingapp.data

import com.nyp.scaffoldingapp.data.local.ExerciseEntity
import com.nyp.scaffoldingapp.data.remote.ExerciseDto
import com.nyp.scaffoldingapp.models.Exercise

private const val SEPARATOR = "|"

// Returns null for a record with no id or name, so one bad record doesn't break the list
fun ExerciseDto.toEntity(): ExerciseEntity? {
    val safeId = id ?: return null
    val safeName = name?.takeIf { it.isNotBlank() } ?: return null
    return ExerciseEntity(
        id = safeId,
        name = safeName,
        category = category.orEmpty(),
        level = level.orEmpty(),
        equipment = equipment.orEmpty(),
        primaryMuscles = primaryMuscles.orEmpty().joinToString(SEPARATOR),
        secondaryMuscles = secondaryMuscles.orEmpty().joinToString(SEPARATOR),
        suggested = suggested.orEmpty(),
        steps = steps.orEmpty().joinToString(SEPARATOR),
        videoUrl = videoUrl.orEmpty()
    )
}

fun ExerciseEntity.toModel() = Exercise(
    id = id,
    name = name,
    category = category,
    level = level,
    equipment = equipment,
    primaryMuscles = primaryMuscles.splitToList(),
    secondaryMuscles = secondaryMuscles.splitToList(),
    suggested = suggested,
    steps = steps.splitToList(),
    videoUrl = videoUrl
)

private fun String.splitToList(): List<String> =
    if (isEmpty()) emptyList() else split(SEPARATOR)