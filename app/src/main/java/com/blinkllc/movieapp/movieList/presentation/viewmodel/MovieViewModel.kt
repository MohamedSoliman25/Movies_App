package com.blinkllc.movieapp.movieList.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.domain.model.Movies
import com.blinkllc.movieapp.movieList.domain.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _moviesInserted = MutableLiveData<Boolean>()
    val moviesInserted: LiveData<Boolean>
        get() = _moviesInserted
    val allMovies = repository.getAllMovies()

//    private val _searchedMovies = MutableStateFlow<List<Movie>>(emptyList())
//    val searchedMovies: StateFlow<List<Movie>> = _searchedMovies
    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

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
//    fun getMoviesByYear(year: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val moviesFlow = repository.getMoviesByYear(year)
//            moviesFlow.collect { movies ->
//                withContext(Dispatchers.Main) {
//                    _moviesLiveData.value = movies
//                }
//            }
//        }
//    }
fun getMoviesByYear(year: Int) {
    viewModelScope.launch(Dispatchers.IO) {
        val movies = repository.getMoviesByYear(year)
        withContext(Dispatchers.Main){
            _moviesLiveData.value = movies
        }
    }
}

    fun getTopRatedMovies(movies:List<Movie>): List<Movie> {
        return movies.sortedByDescending { it.rating }.take(5)
    }

}