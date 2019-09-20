package com.felipeortiz.trailers.models

import androidx.room.*
import com.felipeortiz.trailers.data.network.response.*

@Entity(tableName = "movies")
data class Movie(
    var adult: Boolean,
    var backdrop_path: String,
    var budget: Int,
    var homepage: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var images: Images,
    var imdb_id: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var release_date: String,
    var revenue: Double,
    var runtime: Int,
    var status: String,
    var tagline: String,
    var title: String,
    var video: Boolean,
    var videos: Videos,
    var vote_average: Double,
    var vote_count: Int
)

//data class Video(
//    var id: String,
//    var language: String,
//    var productionCountry: String,
//    var key: String,
//    var name: String,
//    var site: String,
//    var size: Int,
//    var type: String
//)