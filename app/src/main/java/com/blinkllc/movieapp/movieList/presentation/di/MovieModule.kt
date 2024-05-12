package com.blinkllc.movieapp.movieList.presentation.di

import android.content.Context
import androidx.room.Room
import com.blinkllc.movieapp.movieList.data.local.dao.MovieDao
import com.blinkllc.movieapp.movieList.data.local.database.MovieDatabase
import com.blinkllc.movieapp.movieList.data.remote.FlickrApiService
import com.blinkllc.movieapp.movieList.data.repository.FlickrRepositoryImp
import com.blinkllc.movieapp.movieList.data.repository.MovieRepositoryImp
import com.blinkllc.movieapp.movieList.domain.repository.FlickrRepository
import com.blinkllc.movieapp.movieList.domain.repository.MovieRepository
import com.blinkllc.movieapp.movieList.presentation.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule  {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java, "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImp(movieDao)
    }

    @Provides
    fun provideBaseUrl(): String = Utils.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideFlickrApiService(retrofit: Retrofit): FlickrApiService = retrofit.create(FlickrApiService::class.java)

    @Provides
    fun provideFlickrRepository(apiService: FlickrApiService): FlickrRepository = FlickrRepositoryImp(apiService)
}