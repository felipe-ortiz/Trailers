package com.felipeortiz.trailers.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_movies")
data class TrendingMovie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    @PrimaryKey
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