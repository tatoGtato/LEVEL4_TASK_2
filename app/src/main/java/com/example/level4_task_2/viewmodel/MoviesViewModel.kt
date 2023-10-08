package com.example.level4_task_2.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level4_task_2.data.api.util.Resource
import com.example.level4_task_2.data.model.Mov
import com.example.level4_task_2.data.model.Movie
import com.example.level4_task_2.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel (application: Application) : AndroidViewModel(application){
    private val MoviesRepository = MoviesRepository()

    val MoviesResource: LiveData<Resource<Movie>>
        get() = _MoviesResource

    private val _MoviesResource: MutableLiveData<Resource<Movie>> = MutableLiveData(Resource.Empty())

    fun getMovie() {
        //set resource type to loading
        _MoviesResource.value = Resource.Loading()

        viewModelScope.launch {
            _MoviesResource.value = MoviesRepository.getMovies()
        }
    }

    var searchQueryState = mutableStateOf("")

    fun resetQuery() {
        searchQueryState.value = ""
    }

    var query: SnapshotStateList<Mov> = mutableStateListOf<Mov>()


    fun searchFilter(lista : List<Mov>){
        query =  lista.filter { it.title.lowercase().contains(searchQueryState.value)}.toMutableStateList()
    }

    var selectedMovie: Mov? = null
}

