package com.felipeortiz.trailers.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.felipeortiz.trailers.data.network.response.TrendingMovie

@Entity(tableName = "trending_movies")
data class DatabaseTrendingMovie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    @PrimaryKey(autoGenerate = false)
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

fun List<DatabaseTrendingMovie>.toTrendingMovies() : List<TrendingMovie> {
    return map {
        TrendingMovie(
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
    }
}
