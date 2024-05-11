package com.blinkllc.movieapp.movieList.domain.repository

import androidx.lifecycle.LiveData
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun insertMovies(movies: List<Movie>)
    fun getAllMovies(): LiveData<List<Movie>>
    suspend fun getMoviesByYear(year: Int): Flow<List<Movie>>
}