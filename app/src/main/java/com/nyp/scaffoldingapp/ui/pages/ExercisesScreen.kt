package com.nyp.scaffoldingapp.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nyp.scaffoldingapp.ui.components.EmptyView
import com.nyp.scaffoldingapp.ui.components.ErrorView
import com.nyp.scaffoldingapp.ui.components.ExerciseCard
import com.nyp.scaffoldingapp.ui.components.LoadingView
import com.nyp.scaffoldingapp.ui.components.OfflineBanner
import com.nyp.scaffoldingapp.ui.exercises.ExercisesViewModel
import com.nyp.scaffoldingapp.ui.exercises.LoadStatus

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExercisesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    var expandedId by rememberSaveable { mutableStateOf<Int?>(null) }

    val status = state.status
    val hasData = state.totalCount > 0

    Column(modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            label = { Text("Search exercises") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (state.muscles.isNotEmpty()) {
            MuscleFilterRow(
                muscles = state.muscles,
                selected = state.selectedMuscle,
                onSelect = viewModel::onMuscleSelected
            )
            Spacer(Modifier.height(8.dp))
        }

        when {
            // Nothing saved yet, still loading
            !hasData && status is LoadStatus.Loading ->
                LoadingView("Loading exercises…", Modifier.weight(1f))

            // Nothing saved and the request failed: explain it and offer a retry
            !hasData && status is LoadStatus.Failed ->
                ErrorView(status.message, viewModel::refresh, Modifier.weight(1f))

            // Server returned nothing
            !hasData ->
                EmptyView(
                    title = "No exercises yet",
                    message = "There are no exercises to show right now.",
                    modifier = Modifier.weight(1f),
                    actionLabel = "Refresh",
                    onAction = viewModel::refresh
                )

            else -> {
                if (status is LoadStatus.Failed) {
                    OfflineBanner("Offline, showing saved exercises", viewModel::refresh)
                }
                if (status is LoadStatus.Loading) {
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }

                if (state.exercises.isEmpty()) {
                    EmptyView(
                        title = "No matches",
                        message = "No exercises match your search or filter.",
                        modifier = Modifier.weight(1f),
                        actionLabel = "Clear filters",
                        onAction = viewModel::clearFilters
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.exercises, key = { it.id }) { exercise ->
                            ExerciseCard(
                                exercise = exercise,
                                expanded = expandedId == exercise.id,
                                onClick = {
                                    expandedId = if (expandedId == exercise.id) null else exercise.id
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MuscleFilterRow(
    muscles: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selected == null,
                onClick = { onSelect(null) },
                label = { Text("All") }
            )
        }
        items(muscles) { muscle ->
            FilterChip(
                selected = muscle == selected,
                onClick = { onSelect(muscle) },
                label = { Text(muscle.replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}