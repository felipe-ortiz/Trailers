package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.models.NowPlayingMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NowPlayingResponse(
    val page: Int? = null,
    @Json(name = "results")
    val nowPlayingMovieResults: List<NowPlayingMovieResult>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)

@JsonClass(generateAdapter = true)
data class NowPlayingMovieResult(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)

fun NowPlayingResponse.toModelMovies() : Array<NowPlayingMovie> {
    if (nowPlayingMovieResults == null) {
        return emptyArray()
    }

    return nowPlayingMovieResults.map {
        NowPlayingMovie(
            adult = it.adult ?: false,
            backdrop_path = it.backdrop_path ?: "",
            id = it.id,
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
}