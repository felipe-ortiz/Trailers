package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.TrendingDao
import com.felipeortiz.trailers.data.db.entity.TrendingResult
import com.felipeortiz.trailers.data.network.response.MoviesDataSource
import com.felipeortiz.trailers.data.network.response.TrendingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class MovieRespositoryImpl(
    private val trendingDao: TrendingDao,
    private val moviesDataSource: MoviesDataSource
) : MovieRespository {

    init {
        moviesDataSource.downloadedTrending.observeForever{ newTrendingResponse ->
            persistFetchedTrending(newTrendingResponse)
        }
    }

    override suspend fun getTrending(): LiveData<List<TrendingResult>> {
        return withContext(Dispatchers.IO) {
            initTrendingData()
            return@withContext trendingDao.getTrendingResults()
        }
    }

    private fun persistFetchedTrending(newTrendingResponse: TrendingResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            for (result in newTrendingResponse.trendingResults) {
                trendingDao.upsert(result)
            }
        }
    }

    private suspend fun initTrendingData() {
        if (isFetchNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchTrendingData()
        }
    }

    private suspend fun fetchTrendingData() {
        moviesDataSource.fetchTrending("movie", "week")
    }

    private fun isFetchNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}