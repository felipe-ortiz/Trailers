package com.felipeortiz.trailers.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.entity.toTrendingMovies
import com.felipeortiz.trailers.data.network.MovieDbService
import com.felipeortiz.trailers.data.network.response.*
import com.felipeortiz.trailers.internal.Utility
import com.felipeortiz.trailers.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(private val database: MovieDatabase,
                                          private val movieDbService: MovieDbService,
                                          private val context: Context) : MovieRepository {

    private var lastTrendingRequestTime: ZonedDateTime = ZonedDateTime.now().minusDays(1)
    private var lastPopularRequestTime: ZonedDateTime = ZonedDateTime.now().minusDays(1)
    private var lastTopRatedRequestTime: ZonedDateTime = ZonedDateTime.now().minusDays(1)
    private var lastNowPlayingRequestTime: ZonedDateTime = ZonedDateTime.now().minusDays(1)
    private var lastUpcomingRequestTime: ZonedDateTime = ZonedDateTime.now().minusDays(1)

    override val topRatedMovies: LiveData<List<TopRatedMovie>> = database.discoverMoviesDao().getTopRatedMovies()

    override val nowPlayingMovies: LiveData<List<NowPlayingMovie>> = database.discoverMoviesDao().getNowPlayingMovies()

    override val upcomingMovies: LiveData<List<UpcomingMovie>> = database.discoverMoviesDao().getUpcomingMovies()

    override val trendingMovies: LiveData<List<TrendingMovie>> = Transformations.map(database.trendingMoviesDao().getTrendingMovies()) {
        it.toTrendingMovies()
    }

    override val discoverMovies: LiveData<List<DiscoverMovie>> = database.discoverMoviesDao().getAllDiscoverMovies()

    override fun getMovie(movieId: Int) : LiveData<Movie> = database.movieDao().getMovie(movieId)

    override suspend fun getTrendingMovies() {
        if (apiRequestNeeded(lastTrendingRequestTime)) {
            lastTrendingRequestTime = ZonedDateTime.now()
            refreshTrendingMovies()
        }
    }

    private fun apiRequestNeeded(lastFetchTime: ZonedDateTime) : Boolean {
        val oneHourAgo = ZonedDateTime.now().minusHours(1)
        return lastFetchTime.isBefore(oneHourAgo)
    }

    override suspend fun refreshTrendingMovies() {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        } else {
            withContext(Dispatchers.IO) {
                val trendingResponse = movieDbService.getTrending("movie", "day").await()
                val databaseTrendingMoviesArray = trendingResponse.toDatabaseTrendingMoviesArray()
                database.trendingMoviesDao().deleteAll()
                database.trendingMoviesDao().insertAll(*databaseTrendingMoviesArray)
            }
        }
    }

    override suspend fun fetchSelectedMovie(movieId: Int) {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        } else {
            withContext(Dispatchers.IO) {
                val movieResponse = movieDbService.getMovie(movieId).await()
                val movie = movieResponse.toModelMovie()
                database.movieDao().insertAll(movie)
            }
        }
    }

    override suspend fun refreshPopularMovies() {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        } else {
            lastPopularRequestTime = ZonedDateTime.now()
            withContext(Dispatchers.IO) {
                val discoverResponse = movieDbService.getDiscover().await()
                val discoverMovies = discoverResponse.toDiscoverMovies()
                database.discoverMoviesDao().deleteDiscoverMovies()
                database.discoverMoviesDao().insertAll(*discoverMovies)
            }
        }
    }

    override suspend fun refreshTopRatedMovies() {
        when {
            !Utility.isOnline(context) -> {
                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
            }
            apiRequestNeeded(lastTopRatedRequestTime) -> {
                lastTopRatedRequestTime = ZonedDateTime.now()
                Timber.d("Top rated movies requested")
                withContext(Dispatchers.IO) {
                    val response = movieDbService.getTopRatedMovies().await()
                    val movies = response.toModelMovies()
                    database.discoverMoviesDao().insertTopRatedMovies(*movies)
                }
            }
        }
    }

    override suspend fun refreshNowPlayingMovies() {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        } else {
            lastNowPlayingRequestTime = ZonedDateTime.now()
            Timber.d("Now playing movies requested")
            withContext(Dispatchers.IO) {
                val response = movieDbService.getNowPlayingMovies().await()
                val movies = response.toModelMovies()
                database.discoverMoviesDao().insertNowPlayingMovies(*movies)
            }
        }
    }

    override suspend fun refreshUpcomingMovies() {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        } else {
            lastUpcomingRequestTime = ZonedDateTime.now()
            Timber.d("Upcoming movies requested")
            withContext(Dispatchers.IO) {
                val response = movieDbService.getUpcomingMovies().await()
                val movies = response.toModelMovies()
                database.discoverMoviesDao().insertUpcomingMovies(*movies)
            }
        }
    }

    override suspend fun searchMovies(query: String) : List<MovieSearchResult> {

        if (!Utility.isOnline(context)) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
            return emptyList()
        } else {
           return withContext(Dispatchers.Main) {
               val response = movieDbService.getMovieSearch(query = query).await()
               return@withContext response.toModelResult()
           }
        }
    }
}