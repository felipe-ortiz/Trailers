package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.data.db.entity.TrendingMovie
import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int,
    @SerializedName("results")
    val trendingMovies: List<TrendingMovie>,
    val total_pages: Int,
    val total_results: Int
)
