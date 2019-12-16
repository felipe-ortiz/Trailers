package com.felipeortiz.trailers.models

import androidx.room.*
import com.felipeortiz.trailers.data.network.response.*

@Entity(tableName = "movies")
data class Movie(
    val adult: Boolean,
    val backdrops: List<String>,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<String>,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val posters: List<String>,
    val poster_path: String,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val release_date: String,
    val revenue: Double,
    val runtime: Int,
    val spokenLanguages: List<String>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val trailers: List<Trailer>,
    val vote_average: Double,
    val vote_count: Int
)

data class Trailer(
    val id: String,
    val language: String,
    val productionCountry: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)