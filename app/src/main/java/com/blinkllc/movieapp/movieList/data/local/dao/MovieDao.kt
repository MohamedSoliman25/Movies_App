package com.blinkllc.movieapp.movieList.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE year = :year")
    fun getMoviesByYear(year: Int): Flow<List<Movie>>
}