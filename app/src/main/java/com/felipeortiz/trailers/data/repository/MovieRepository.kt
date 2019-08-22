package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.entity.DatabaseMovie
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

    fun getMovie(movieId: Int) : LiveData<Movie> = Transformations.map(database.movieDao.getMovie(movieId)) {
        it?.let {
            Movie(adult = it.adult,
                backdrop_path = it.backdrop_path,
                budget = it.budget,
                id = it.id,
                imdb_id = it.imdb_id,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                revenue = it.revenue,
                runtime = it.runtime,
                tagline = it.tagline,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count)
        }
    }

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
            val trendingResponse = Network.movieDbService.getTrending("all", "week").await()
            val databaseTrendingMoviesArray = trendingResponse.toDatabaseTrendingMoviesArray()
            database.trendingMoviesDao.insertAll(*databaseTrendingMoviesArray)
        }
    }

    suspend fun fetchSelectedMovie(movieId: Int) {
        withContext(Dispatchers.IO) {
            val movieResponse = Network.movieDbService.getMovie(movieId).await()
            val dbMovie = DatabaseMovie(
                adult = movieResponse.adult,
                backdrop_path = movieResponse.backdrop_path,
                budget = movieResponse.budget,
                id = movieResponse.id,
                imdb_id = movieResponse.imdb_id,
                original_language = movieResponse.original_language,
                original_title = movieResponse.original_title,
                overview = movieResponse.overview,
                popularity = movieResponse.popularity,
                poster_path = movieResponse.poster_path,
                release_date = movieResponse.release_date,
                revenue = movieResponse.revenue,
                runtime = movieResponse.runtime,
                tagline = movieResponse.tagline,
                title = movieResponse.title,
                video = movieResponse.video,
                vote_average = movieResponse.vote_average,
                vote_count = movieResponse.vote_count
            )
            database.movieDao.insertAll(dbMovie)
        }
    }
}