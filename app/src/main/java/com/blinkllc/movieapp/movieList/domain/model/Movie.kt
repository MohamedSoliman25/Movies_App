package com.blinkllc.movieapp.movieList.domain.model

data class Movie(
    var title: String,
    var year: Int,
    var cast: List<String> = emptyList(),
    var genres: List<String> = emptyList(),
    var rating: Int,
)