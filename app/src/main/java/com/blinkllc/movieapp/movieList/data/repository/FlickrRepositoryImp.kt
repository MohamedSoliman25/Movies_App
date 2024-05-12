package com.blinkllc.movieapp.movieList.data.repository

import com.blinkllc.movieapp.movieList.data.remote.FlickrApiService
import com.blinkllc.movieapp.movieList.domain.model.FlickrMovieResponse
import com.blinkllc.movieapp.movieList.domain.model.MoviePhotosResponse
import com.blinkllc.movieapp.movieList.domain.repository.FlickrRepository
import retrofit2.Response
import javax.inject.Inject

class FlickrRepositoryImp @Inject constructor(private val flickrApiService: FlickrApiService) :FlickrRepository{

    override suspend fun fetchMoviePhotos(
        apiKey: String,
        movieName: String,
        page: Int,
        perPage:Int
    ): Response<FlickrMovieResponse> {
        return flickrApiService.fetchMoviePhotos(apiKey,movieName,page,perPage)
    }
}