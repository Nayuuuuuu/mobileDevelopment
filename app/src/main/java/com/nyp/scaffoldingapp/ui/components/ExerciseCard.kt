package com.nyp.scaffoldingapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyp.scaffoldingapp.models.Exercise

@Composable
fun ExerciseCard(
    exercise: Exercise,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(12.dp)) {
            // Category tile, like PUSH / PULL / LEGS in your reference design
            Box(
                modifier = Modifier
                    .size(width = 88.dp, height = 72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = exercise.category.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(exercise.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${exercise.level.capitalised()} · ${exercise.equipment.capitalised()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Targets: " + exercise.primaryMuscles.joinToString { it.capitalised() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // The expand animation: the card grows to show the steps
                AnimatedVisibility(visible = expanded) {
                    ExerciseDetails(exercise)
                }
            }
        }
    }
}

@Composable
private fun ExerciseDetails(exercise: Exercise) {
    Column(Modifier.padding(top = 12.dp)) {
        if (exercise.suggested.isNotBlank()) {
            Text("Suggested: ${exercise.suggested}", style = MaterialTheme.typography.bodyMedium)
        }
        if (exercise.secondaryMuscles.isNotEmpty()) {
            Text(
                text = "Also works: " + exercise.secondaryMuscles.joinToString { it.capitalised() },
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (exercise.steps.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Steps", style = MaterialTheme.typography.titleSmall)
            exercise.steps.forEachIndexed { index, step ->
                Text("${index + 1}. $step", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private fun String.capitalised() = replaceFirstChar { it.uppercase() }