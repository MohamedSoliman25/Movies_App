package com.blinkllc.movieapp.movieList.domain.repository

import com.blinkllc.movieapp.movieList.domain.model.FlickrMovieResponse
import com.blinkllc.movieapp.movieList.domain.model.MoviePhotosResponse
import retrofit2.Response

interface FlickrRepository {
    suspend fun fetchMoviePhotos(apiKey: String, query: String, page: Int,perPage:Int): Response<FlickrMovieResponse>
}