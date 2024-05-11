package com.blinkllc.movieapp.movieList.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.domain.model.Movies
import com.blinkllc.movieapp.movieList.domain.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _moviesInserted = MutableLiveData<Boolean>()
    val moviesInserted: LiveData<Boolean>
        get() = _moviesInserted
    val allMovies = repository.getAllMovies()

    private val _searchedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val searchedMovies: StateFlow<List<Movie>> = _searchedMovies

    fun insertMoviesFromJson(jsonString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies: Movies = Gson().fromJson(jsonString, object : TypeToken<Movies>() {}.type)
            val myMovies = movies.movies.map {
                Movie(0,it.title,it.year,it.cast,it.genres,it.rating)
            }
            repository.insertMovies(myMovies)
            withContext(Dispatchers.Main){
                _moviesInserted.value = true

            }
        }
    }
    fun searchMovies(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMoviesByYear(year).collect {
                withContext(Dispatchers.Main){
                    _searchedMovies.value = getTopRatedMovies(it)
                }
            }
        }
    }
    fun getTopRatedMovies(movies:List<Movie>): List<Movie> {
        return movies.sortedByDescending { it.rating }.take(5)
    }
}