package com.blinkllc.movieapp.movieList.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blinkllc.movieapp.databinding.ActivityMainBinding
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.presentation.Utils.MOVIE_DATA
import com.blinkllc.movieapp.movieList.presentation.adapters.MovieListAdapter
import com.blinkllc.movieapp.movieList.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        // Read JSON file from assets folder
        val jsonString = application.assets.open("movies.json").bufferedReader().use { it.readText() }
        // Insert movies into database
        viewModel.insertMoviesFromJson(jsonString)

       observeInsertedMovie()
        searchMoviesByYear()
        observeMoviesByYear()

    }
    private fun observeInsertedMovie(){
        viewModel.moviesInserted.observe(this) { inserted ->
            if (inserted) {
                observeAllMovies()
            }
        }
    }
    private fun observeAllMovies(){
        viewModel.allMovies.observe(this){movies->
            Log.d(TAG, "observeAllMovies: "+movies)
            getMoviesAdapter()?.updateData(movies)
        }
    }

    private fun initRecyclerView() {
        binding.movieSearchResult.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MovieListAdapter(ArrayList(), ::onItemClick)
        }
    }
    private fun onItemClick(movie: Movie) {
        goToMovieDetails(movie)
    }
    private fun getMoviesAdapter() =
        binding.movieSearchResult.adapter as? MovieListAdapter

    private fun searchMoviesByYear() {

        binding.searchMovieEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = binding.searchMovieEditText.text.toString()
                getMoviesByYear(searchText.toInt())
                hideKeyboard()
                true
            } else {
                false
            }
        }

        //        binding.searchMovieEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                Log.d(TAG, "onTextChangedjbjhvv: ")
//                getMoviesByYear(s.toString().toInt())
//                hideKeyboard()
//            }
//        })
    }
    private fun observeMoviesByYear(){
            viewModel.moviesLiveData.observe(this@MainActivity){
                if (it.isNotEmpty()) {
                    getMoviesAdapter()?.updateData(emptyList())
                    getMoviesAdapter()?.updateData(it)
                }
            }
    }
    private fun getMoviesByYear(year:Int){
        viewModel.getMoviesByYear(year)
    }

    private fun hideKeyboard(){
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(binding.searchMovieEditText.windowToken, 0)
    }

    private fun goToMovieDetails(movie: Movie){
        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_DATA, movie)
        startActivity(intent)
    }
}