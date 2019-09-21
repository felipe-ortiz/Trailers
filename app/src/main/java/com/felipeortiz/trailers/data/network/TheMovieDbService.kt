package com.felipeortiz.trailers.data.network

import com.felipeortiz.trailers.data.network.response.MovieResponse
import com.felipeortiz.trailers.data.network.response.TrendingResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {
    @GET("trending/{media_type}/{time_window}")
    fun getTrending(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String
    ) : Deferred<TrendingResponse>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int,
                 @Query("append_to_response") videos: String = "videos,images") : Deferred<MovieResponse>
}