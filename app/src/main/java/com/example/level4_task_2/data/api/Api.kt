package com.example.level4_task_2.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {
    companion object {
        const val MOVIE_BASE_URL = "https://api.themoviedb.org"

        // the lazy keyword makes sure the createApi function is not called until these properties are accessed
        val catsClient by lazy { createApi(MOVIE_BASE_URL) }

        fun createApi(baseUrl: String): ApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }.build()

            // Create the Retrofit instance
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

//val client = OkHttpClient()
//
//val request = Request.Builder()
//    .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
//    .get()
//    .addHeader("accept", "application/json")
//    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OWVlMzJlNmM1MzZiZTcwNTQ1NzZjNDM1YWE3YjM2MyIsInN1YiI6IjY1MWVjYjA4OTY3Y2M3MzQyNWYyZDc5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8IHPFAmiu9gMQqk_kAE0UVYdg7A-S92rpSTy0djFdf0")
//    .build()
//
//val response = client.newCall(request).execute()
