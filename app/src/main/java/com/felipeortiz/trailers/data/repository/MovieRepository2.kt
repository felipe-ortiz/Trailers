package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.entity.TrendingMovie
import com.felipeortiz.trailers.data.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository2(private val database: MovieDatabase) {
    val trendingMovies: LiveData<List<TrendingMovie>> = database.trendingDao.getTrendingMovies()

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val trendingResponse = Network.movieDbService.getTrending("movie", "week").await()
            database.trendingDao.insertAll(*trendingResponse.trendingMovies.toTypedArray())
        }
    }

}