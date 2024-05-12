package com.blinkllc.movieapp.movieList.data.repository

import androidx.lifecycle.LiveData
import com.blinkllc.movieapp.movieList.data.local.dao.MovieDao
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val movieDao: MovieDao) :MovieRepository{
    override suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

    override fun getAllMovies(): LiveData<List<Movie>> = movieDao.getAllMovies()
    override suspend fun getMoviesByYear(year: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            movieDao.getMoviesByYear(year)
        }
    }

}