package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.network.response.TrendingMovie
import com.felipeortiz.trailers.models.*

interface MovieRepository {

    val trendingMovies: LiveData<List<TrendingMovie>>

    val discoverMovies: LiveData<List<DiscoverMovie>>

    val topRatedMovies: LiveData<List<TopRatedMovie>>

    val upcomingMovies: LiveData<List<UpcomingMovie>>

    val nowPlayingMovies: LiveData<List<NowPlayingMovie>>

    fun getMovie(movieId: Int) : LiveData<Movie>

    suspend fun getTrendingMovies()

    suspend fun refreshTrendingMovies()

    suspend fun fetchSelectedMovie(movieId: Int)

    suspend fun refreshPopularMovies()

    suspend fun refreshTopRatedMovies()

    suspend fun refreshNowPlayingMovies()

    suspend fun refreshUpcomingMovies()

    suspend fun searchMovies(query: String) : List<MovieSearchResult>
}