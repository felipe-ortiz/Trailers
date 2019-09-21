package com.felipeortiz.trailers.di

import android.content.Context
import com.felipeortiz.trailers.data.db.MovieDao
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.TrendingMoviesDao
import com.felipeortiz.trailers.data.network.MovieDbService
import com.felipeortiz.trailers.data.repository.MovieRepository
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [AppModule::class, ServiceModule::class])
@Singleton
interface AppComponent {

    fun appContext(): Context

    fun movieRepository(): MovieRepository

    fun movieService(): MovieDbService

    fun retrofit(): Retrofit

    fun trendingDao(): TrendingMoviesDao

    fun movieDao(): MovieDao

}