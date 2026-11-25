package com.nyp.scaffoldingapp.data

import com.nyp.scaffoldingapp.data.local.ExerciseDao
import com.nyp.scaffoldingapp.data.remote.ExerciseApi
import com.nyp.scaffoldingapp.models.Exercise
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed interface RefreshResult {
    object Success : RefreshResult
    data class Failure(val message: String) : RefreshResult
}

class ExerciseRepository(
    private val dao: ExerciseDao,
    private val api: ExerciseApi
) {
    // The screen always reads from the database. The network only refreshes it.
    val exercises: Flow<List<Exercise>> =
        dao.observeAll().map { list -> list.map { it.toModel() } }

    suspend fun refresh(): RefreshResult = try {
        val fresh = api.getExercises().mapNotNull { it.toEntity() }
        dao.replaceAll(fresh)
        RefreshResult.Success
    } catch (e: CancellationException) {
        throw e   // never swallow cancellation
    } catch (e: SocketTimeoutException) {
        RefreshResult.Failure("The server is taking too long to respond. Please try again.")
    } catch (e: IOException) {
        RefreshResult.Failure("No internet connection. Check your connection and try again.")
    } catch (e: HttpException) {
        RefreshResult.Failure("The server had a problem (code ${e.code()}). Please try again in a moment.")
    } catch (e: Exception) {
        RefreshResult.Failure("Something went wrong loading the exercises.")
    }
}