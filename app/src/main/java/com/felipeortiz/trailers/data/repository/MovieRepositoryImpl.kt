package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.TrendingDao
import com.felipeortiz.trailers.data.db.entity.TrendingMovie
import com.felipeortiz.trailers.data.network.response.MoviesDataSource
import com.felipeortiz.trailers.data.network.response.TrendingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime
import timber.log.Timber

class MovieRepositoryImpl(private val trendingDao: TrendingDao,
                          private val movieCache: MoviesDataSource) : MovieRespository {

    init {
        movieCache.downloadedTrending.observeForever { newTrendingResponse ->
            persistFetchedTrending(newTrendingResponse)
        }
    }

    override suspend fun getTrending(): LiveData<List<TrendingMovie>> {
        initTrendingData()
        val trendingMovies = trendingDao.getTrendingMovies()
        trendingMovies.value?.let {
            for (movie in it) {
                Timber.d("Movie from db: ${movie.title}")
            }
        }
        return trendingDao.getTrendingMovies()
    }

    private fun persistFetchedTrending(newTrendingResponse: TrendingResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            for (result in newTrendingResponse.trendingMovies) {
                trendingDao.insertAll(result)
                // insert works
                //Timber.d("Inserted: ${result.title}")
            }
        }
    }

    // TODO: Fix this so it doesn't load every time
    private suspend fun initTrendingData() {
        if (isFetchNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchTrendingData()
            Timber.d("Fetched From Db")
        }
    }

    private suspend fun fetchTrendingData() {
        movieCache.fetchTrending("movie", "week")
    }

    private fun isFetchNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}