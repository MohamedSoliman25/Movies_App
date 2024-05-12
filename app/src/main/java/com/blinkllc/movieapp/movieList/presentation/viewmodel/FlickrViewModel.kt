package com.blinkllc.movieapp.movieList.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkllc.movieapp.movieList.domain.model.FlickrMovieResponse
import com.blinkllc.movieapp.movieList.domain.model.MoviePhotosResponse
import com.blinkllc.movieapp.movieList.domain.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(private val repository: FlickrRepository) : ViewModel() {
    private val _moviePhoto = MutableLiveData<FlickrMovieResponse>()
    val moviePhoto: LiveData<FlickrMovieResponse> = _moviePhoto

    fun fetchMoviePhotos(apiKey: String, query: String, page: Int,perPage:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchMoviePhotos(apiKey, query, page,perPage)
                if (response.isSuccessful){
                    response.body()?.let {moviePhoto->
                        withContext(Dispatchers.Main){
                            _moviePhoto.value = moviePhoto
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("TAG", "fetchMoviePhoto: "+e.message)
            }
        }
    }
}