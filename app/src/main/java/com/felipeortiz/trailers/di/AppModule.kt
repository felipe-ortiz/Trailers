package com.felipeortiz.trailers.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.felipeortiz.trailers.data.db.MovieDao
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.TrendingMoviesDao
import com.felipeortiz.trailers.data.network.MovieDbService
import com.felipeortiz.trailers.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    private val database: MovieDatabase =
        Room.databaseBuilder(app, MovieDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideDatabase() = database

    @Provides
    @Singleton
    fun provideRepository(movieDbService: MovieDbService): MovieRepository = MovieRepository(database, movieDbService)

    @Provides
    @Singleton
    fun provideTrendingDao(database: MovieDatabase): TrendingMoviesDao {
        return database.trendingMoviesDao()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}