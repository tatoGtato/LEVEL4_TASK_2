package com.example.level4_task_2.repository

import android.util.Log
import com.example.level4_task_2.data.api.Api
import com.example.level4_task_2.data.api.Api.Companion.MOVIE_BASE_URL
import com.example.level4_task_2.data.api.ApiService
import com.example.level4_task_2.data.api.util.Resource
import com.example.level4_task_2.data.model.Movie
import kotlinx.coroutines.withTimeout

class MoviesRepository {
    private val apiService: ApiService = Api.createApi(MOVIE_BASE_URL)

    suspend fun getMovies() : Resource<Movie> {

        val response = try {
            withTimeout(5_000) {
                apiService.getMovies()
            }
        } catch(e: Exception) {
            Log.v("NumbersRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }
}