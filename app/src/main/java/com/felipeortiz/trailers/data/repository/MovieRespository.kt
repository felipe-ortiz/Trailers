package com.felipeortiz.trailers.data.repository

import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.entity.TrendingMovie

interface MovieRespository {

    suspend fun getTrending(): LiveData<List<TrendingMovie>>
}