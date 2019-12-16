package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.models.DiscoverMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverResponse(
    val page: Int?,
    @Json(name = "results")
    val discoverMovieResults: List<DiscoverMovieResult>?,
    val total_pages: Int?,
    val total_results: Int?
)

@JsonClass(generateAdapter = true)
data class DiscoverMovieResult(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int?>?,
    val id: Int?,
    val original_language: String?,
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

fun DiscoverResponse.toDiscoverMovies() : Array<DiscoverMovie> {
    return when(discoverMovieResults != null) {
        true -> discoverMovieResults.map {
            DiscoverMovie(
                adult = it.adult ?: false,
                backdrop_path = it.backdrop_path ?: "",
                id = it.id ?: 0,
                original_language = it.original_language ?: "",
                original_title = it.original_title ?: "",
                overview = it.overview ?: "",
                popularity = it.popularity ?: 0.0,
                poster_path = it.poster_path ?: "",
                release_date = it.release_date ?: "",
                title = it.title ?: "",
                video = it.video ?: false,
                vote_average = it.vote_average ?: 0.0,
                vote_count = it.vote_count ?: 0
            )
        }.toTypedArray()
        false -> emptyArray()
    }
}