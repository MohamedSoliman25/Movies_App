package com.blinkllc.movieapp.movieList.data.remote

import com.blinkllc.movieapp.movieList.domain.model.FlickrMovieResponse
import com.blinkllc.movieapp.movieList.domain.model.MoviePhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
//    "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key={your_api_key}&format=json&nojsoncallback=1&text{movie_title}&page=1&per_page=10"

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun fetchMoviePhotos(
        @Query("api_key") apiKey: String,
        @Query("text") movieTitle: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<FlickrMovieResponse>
}