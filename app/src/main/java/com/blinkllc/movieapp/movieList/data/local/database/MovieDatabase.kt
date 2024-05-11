package com.blinkllc.movieapp.movieList.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blinkllc.movieapp.movieList.data.local.dao.MovieDao
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.data.local.entities.MyListConverter

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(MyListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}