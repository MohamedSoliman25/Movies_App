package com.blinkllc.movieapp.movieList.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blinkllc.movieapp.R
import com.blinkllc.movieapp.databinding.ActivityMovieDetailsBinding
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.domain.model.Photo
import com.blinkllc.movieapp.movieList.domain.model.Photos
import com.blinkllc.movieapp.movieList.presentation.Utils
import com.blinkllc.movieapp.movieList.presentation.adapters.MovieListAdapter
import com.blinkllc.movieapp.movieList.presentation.adapters.PhotoListAdapter
import com.blinkllc.movieapp.movieList.presentation.viewmodel.FlickrViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var activityMovieDetailsBinding:ActivityMovieDetailsBinding
    private val viewModel:FlickrViewModel by viewModels()
    private var movie:Movie? = null
    private val TAG = "MovieDetailsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(activityMovieDetailsBinding.root)
        receiveMovie()
        observeFlickrPhoto()
        initRecyclerView()

    }
    private fun receiveMovie(){
        val extras = intent.extras
        if (extras != null) {
            movie = extras.getSerializable(Utils.MOVIE_DATA) as Movie
            movie?.let {
                displayMovieDetails(it)
                viewModel.fetchMoviePhotos(Utils.API_KEY,it.title,1,10)
            }
        }
    }
    private fun displayMovieDetails(movie: Movie){
        activityMovieDetailsBinding.apply {
        title.text = movie.title
            year.text = movie.year.toString()
            if (movie.genres.isNotEmpty()){
                genres.text = movie.genres[0]
            }
            if (movie.cast.isNotEmpty()){
                cast.text = movie.cast[0]
            }
        }
    }
    private fun observeFlickrPhoto(){
        viewModel.moviePhoto.observe(this){
            Log.d(TAG, "observeFlickrPhoto: "+it.photos.photo.size)
            if (it.photos.photo.isNotEmpty()){
                getPhotosAdapter()?.updateData(it.photos.photo)
            }
//            displayImage(it.photos)
        }
    }

//    private fun displayImage(photos: Photos){
//        if (photos.photo.isNotEmpty()&&photos.photo.size >=2){
//            val photoList = photos.photo
//            //"https://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg"
//            val imageOneUrl = "https://farm${photoList[0].farm}.static.flickr.com/${photoList[0].server}/${photoList[0].id}_${photoList[0].secret}.jpg"
//            val imageTwoUrl = "https://farm${photoList[1].farm}.static.flickr.com/${photoList[1].server}/${photoList[1].id}_${photoList[1].secret}.jpg"
//            activityMovieDetailsBinding.apply {
//                fetchImageWithGlide(imageOneUrl,movieImgOne)
//                fetchImageWithGlide(imageTwoUrl,movieImgTwo)
//            }
//
//            }
//
//
//
//    }
//    private fun fetchImageWithGlide(imageUrl:String,movieImg:ImageView){
//            // Load image into an ImageView
//            Glide.with(this@MovieDetailsActivity)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Cache image
//                .into(movieImg)
//
//    }
    private fun initRecyclerView() {
        activityMovieDetailsBinding.rvPhotos.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = PhotoListAdapter(ArrayList())
        }
    }
    private fun getPhotosAdapter() =
        activityMovieDetailsBinding.rvPhotos.adapter as? PhotoListAdapter
}