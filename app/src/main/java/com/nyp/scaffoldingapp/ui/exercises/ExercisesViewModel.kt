package com.nyp.scaffoldingapp.ui.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nyp.scaffoldingapp.data.ExerciseRepository
import com.nyp.scaffoldingapp.data.RefreshResult
import com.nyp.scaffoldingapp.data.local.AppDatabase
import com.nyp.scaffoldingapp.data.remote.ApiClient
import com.nyp.scaffoldingapp.models.Exercise
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface LoadStatus {
    object Loading : LoadStatus
    object Loaded : LoadStatus
    data class Failed(val message: String) : LoadStatus
}

data class ExercisesUiState(
    val status: LoadStatus = LoadStatus.Loading,
    val exercises: List<Exercise> = emptyList(),   // after search and filter
    val totalCount: Int = 0,                       // before search and filter
    val muscles: List<String> = emptyList(),
    val selectedMuscle: String? = null
)

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExerciseRepository(
        dao = AppDatabase.get(application).exerciseDao(),
        api = ApiClient.exerciseApi
    )

    private val status = MutableStateFlow<LoadStatus>(LoadStatus.Loading)
    private val selectedMuscle = MutableStateFlow<String?>(null)

    // Kept separate from uiState so typing in the search box never lags
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val uiState: StateFlow<ExercisesUiState> = combine(
        repository.exercises, status, _query, selectedMuscle
    ) { all, loadStatus, text, muscle ->
        ExercisesUiState(
            status = loadStatus,
            exercises = all.filter { it.matches(text, muscle) },
            totalCount = all.size,
            muscles = all.flatMap { it.primaryMuscles }.distinct().sorted(),
            selectedMuscle = muscle
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ExercisesUiState())

    private var refreshJob: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        if (refreshJob?.isActive == true) return   // ignore taps while already loading
        refreshJob = viewModelScope.launch {
            status.value = LoadStatus.Loading
            status.value = when (val result = repository.refresh()) {
                is RefreshResult.Success -> LoadStatus.Loaded
                is RefreshResult.Failure -> LoadStatus.Failed(result.message)
            }
        }
    }

    fun onQueryChange(text: String) {
        _query.value = text
    }

    fun onMuscleSelected(muscle: String?) {
        selectedMuscle.value = muscle
    }

    fun clearFilters() {
        _query.value = ""
        selectedMuscle.value = null
    }

    private fun Exercise.matches(text: String, muscle: String?): Boolean =
        (text.isBlank() || name.contains(text.trim(), ignoreCase = true)) &&
                (muscle == null || muscle in primaryMuscles)
}