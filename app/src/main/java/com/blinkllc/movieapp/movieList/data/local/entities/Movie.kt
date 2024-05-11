package com.blinkllc.movieapp.movieList.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var year: Int,
    var cast: List<String>,
    var genres: List<String>,
    var rating: Int
)