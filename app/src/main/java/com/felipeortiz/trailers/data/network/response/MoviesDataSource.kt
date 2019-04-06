package com.felipeortiz.trailers.data.network.response

import androidx.lifecycle.LiveData

interface MoviesDataSource {
    val downloadedTrending: LiveData<TrendingResponse>

    suspend fun fetchTrending(mediaType: String, timeWindow: String)
}