package com.felipeortiz.trailers.data.network.response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.felipeortiz.trailers.data.network.MovieDbService
import com.felipeortiz.trailers.internal.NoConnectivityException

class MoviesDataSourceImpl(private val movieDbService: MovieDbService) : MoviesDataSource {

    private val _downloadedTrending = MutableLiveData<TrendingResponse>()

    override val downloadedTrending: LiveData<TrendingResponse>
        get() = _downloadedTrending

    override suspend fun fetchTrending(mediaType: String, timeWindow: String) {
        try {
            val fetchTrendingResponse = movieDbService.getTrending(mediaType, timeWindow).await()
            _downloadedTrending.postValue(fetchTrendingResponse)
        } catch (e : NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}