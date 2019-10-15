package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.data.db.entity.DatabaseTrendingMovie
import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int,
    @SerializedName("results")
    val trendingMovies: List<TrendingMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TrendingMovie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val id: Int,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun TrendingResponse.toDatabaseTrendingMoviesArray() : Array<DatabaseTrendingMovie> {
    return trendingMovies.map {
        DatabaseTrendingMovie(
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            first_air_date = it.first_air_date,
            id = it.id,
            name = it.name,
            original_language = it.original_language,
            original_title = it.original_title,
            original_name = it.original_name,
            overview = it.overview,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )
    }.toTypedArray()
}

