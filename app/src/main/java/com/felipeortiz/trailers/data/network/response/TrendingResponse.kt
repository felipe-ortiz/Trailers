package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.data.db.entity.TrendingResult
import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int,
    @SerializedName("result")
    val trendingResults: List<TrendingResult>,
    val total_pages: Int,
    val total_results: Int
)