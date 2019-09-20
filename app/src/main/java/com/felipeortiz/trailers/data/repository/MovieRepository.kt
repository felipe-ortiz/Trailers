package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.entity.toTrendingMovies
import com.felipeortiz.trailers.data.network.Network
import com.felipeortiz.trailers.data.network.response.TrendingMovie
import com.felipeortiz.trailers.data.network.response.toDatabaseTrendingMoviesArray
import com.felipeortiz.trailers.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class MovieRepository(private val database: MovieDatabase) {

    private var lastFetchTime: ZonedDateTime = ZonedDateTime.now()
    val trendingMovies: LiveData<List<TrendingMovie>> = Transformations.map(database.trendingMoviesDao.getTrendingMovies()) {
        it.toTrendingMovies()
    }

    fun getMovie(movieId: Int) : LiveData<Movie> = database.movieDao.getMovie(movieId)

    suspend fun getTrendingMovies() {
//        if (isTrendingMoviesFetchNeeded(lastFetchTime)) {
//            lastFetchTime = ZonedDateTime.now()
//            refreshTrendingMovies()
//        }
        refreshTrendingMovies()
    }

    private fun isTrendingMoviesFetchNeeded(lastFetchTime: ZonedDateTime) : Boolean {
        val oneHourAgo = ZonedDateTime.now().minusHours(1)
        return lastFetchTime.isBefore(oneHourAgo)
    }

    private suspend fun refreshTrendingMovies() {
        withContext(Dispatchers.IO) {
            val trendingResponse = Network.movieDbService.getTrending("movie", "week").await()
            val databaseTrendingMoviesArray = trendingResponse.toDatabaseTrendingMoviesArray()
            database.trendingMoviesDao.deleteAll()
            database.trendingMoviesDao.insertAll(*databaseTrendingMoviesArray)
        }
    }

    suspend fun fetchSelectedMovie(movieId: Int) {
        withContext(Dispatchers.IO) {
            val movieResponse = Network.movieDbService.getMovie(movieId).await()
            val movie = movieResponse.toModelMovie()
            database.movieDao.insertAll(movie)
        }
    }
}