package com.example.level4_task_2.data.api

import com.example.level4_task_2.data.model.Mov
import com.example.level4_task_2.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OWVlMzJlNmM1MzZiZTcwNTQ1NzZjNDM1YWE3YjM2MyIsInN1YiI6IjY1MWVjYjA4OTY3Y2M3MzQyNWYyZDc5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8IHPFAmiu9gMQqk_kAE0UVYdg7A-S92rpSTy0djFdf0")
    @GET("/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getMovies(): Movie
}