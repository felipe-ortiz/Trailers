package com.felipeortiz.trailers.data.network

import com.felipeortiz.trailers.data.network.response.*
import com.felipeortiz.trailers.models.UpcomingMovie
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

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("language") language: String = "en-US",
                    @Query("page") page: Int = 1,
                    @Query("region") region: String = "US"
    ) : Deferred<TopRatedMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("language") language: String = "en-US",
                        @Query("page") page: Int = 1,
                        @Query("region") region: String = "US"
    ) : Deferred<NowPlayingResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("language") language: String = "en-US",
                          @Query("page") page: Int = 1,
                          @Query("region") region: String = "US"
    ) : Deferred<UpcomingMoviesResponse>

    @GET("discover/movie")
    fun getDiscover(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("page") page: Int = 1
    ) : Deferred<DiscoverResponse>

    @GET("search/movie")
    fun getMovieSearch(
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
    ) : Deferred<MovieSearchResponse>
}
