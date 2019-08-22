package com.felipeortiz.trailers.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.felipeortiz.trailers.models.Movie

@Entity(tableName = "movies")
data class DatabaseMovie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Int?,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Double?,
    val runtime: Int?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun List<DatabaseMovie>.toMovies() : List<Movie> {
    return map {
        Movie(
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            budget = it.budget,
            id = it.id,
            imdb_id = it.imdb_id,
            original_language = it.original_language,
            original_title = it.original_title,
            overview = it.overview,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            revenue = it.revenue,
            runtime = it.runtime,
            tagline = it.tagline,
            title = it.title,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )
    }
}