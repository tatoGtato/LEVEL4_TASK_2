package com.example.level4_task_2.data.model

import androidx.compose.runtime.MutableState
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Mov>,
//    @SerializedName("title") val title: String,
//    @SerializedName("backdrop_path") val backdrop: String,
//    @SerializedName("poster_path") val poster: String,
//    @SerializedName("release_date") val release: String,
//    @SerializedName("vote_average") val rating: String,
//    @SerializedName("overview") val overview: String,
)

data class Mov(
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("release_date") val release: String,
    @SerializedName("vote_average") val rating: String,
    @SerializedName("overview") val overview: String,
)
