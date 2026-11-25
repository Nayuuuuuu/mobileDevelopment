package com.nyp.scaffoldingapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ExerciseApi {
    @GET("/")
    suspend fun getExercises(): List<ExerciseDto>
}

object ApiClient {
    private const val BASE_URL = "https://lelys0.mockapi.dog/"

    // Timeouts: a slow server fails with a message instead of hanging forever
    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .callTimeout(15, TimeUnit.SECONDS)
        .build()

    val exerciseApi: ExerciseApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApi::class.java)
    }
}