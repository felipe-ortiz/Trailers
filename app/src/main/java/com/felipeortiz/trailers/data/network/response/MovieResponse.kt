package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.models.Movie
import com.felipeortiz.trailers.models.Trailer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Int?,
    val genres: List<GenreResponse>?,
    val id: Int?,
    val images: ImagesResponse?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Double?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val videos: Videos?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    fun toModelMovie() : Movie {
            return Movie(
            adult = this.adult ?: false,
            backdrops = this.backdrops(),
            backdrop_path = this.backdrop_path ?: "",
            budget = this.budget ?: 0,
            id = this.id ?: 0,
            imdb_id = this.imdb_id ?: "",
            genres = this.genres(),
            original_language = this.original_language ?: "",
            original_title = this.original_title ?: "",
            overview = this.overview ?: "",
            popularity = this.popularity ?: 0.0,
            posters = this.posters(),
            poster_path = this.poster_path ?: "",
            productionCompanies = this.productionCompanies(),
            productionCountries = this.productionCountries(),
            release_date = this.release_date ?: "",
            revenue = this.revenue ?: 0.0,
            runtime = this.runtime ?: 0,
            spokenLanguages = this.spokenLanguages(),
            status = this.status ?: "",
            tagline = this.tagline ?: "",
            title = this.title ?: "",
            video = this.video ?: false,
            trailers = if (videos != null) videos.toTrailers() else emptyList(),
            vote_average = this.vote_average ?: 0.0,
            vote_count = this.vote_count ?: 0
        )
    }
}

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    val trailers: List<TrailerResponse>?
)

fun Videos.toTrailers() : List<Trailer> {
    return when (trailers != null) {
        true -> {
            trailers.map {
                Trailer(
                    id = it.id ?: "",
                    productionCountry = it.iso_3166_1 ?: "",
                    language = it.iso_639_1 ?: "",
                    key = it.key ?: "",
                    name = it.name ?: "",
                    site = it.site ?: "",
                    size = it.size ?: 0,
                    type = it.type ?: ""
                )
            }
        }
        false -> emptyList()
    }
}

fun MovieResponse.backdrops() : List<String> {
    return when(images != null && images.backdrops != null) {
        true -> images.backdrops.map { it.file_path ?: "" }
        false -> emptyList()
    }
}

fun MovieResponse.posters() : List<String> {
    return when(images != null && images.posters != null) {
        true -> images.posters.map { it.file_path ?: "" }
        false -> emptyList()
    }
}

fun MovieResponse.genres(): List<String> {
    return when(genres != null) {
        true -> genres.map { it.name ?: "" }
        false -> emptyList()
    }
}

fun MovieResponse.productionCompanies() : List<String> {
    return when (production_companies != null) {
        true -> production_companies.map { it.name ?: "" }
        false -> emptyList()
    }
}

fun MovieResponse.productionCountries(): List<String> {
    return when (production_countries != null) {
        true -> production_countries.map { it.name ?: "" }
        false -> emptyList()
    }
}

fun MovieResponse.spokenLanguages(): List<String> {
    return when(spoken_languages != null) {
        true -> spoken_languages.map { it.name ?: "" }
        false -> emptyList()
    }
}

@JsonClass(generateAdapter = true)
data class TrailerResponse(
    val id: String?,
    val iso_3166_1: String?,
    val iso_639_1: String?,
    val key: String?,
    val name: String?,
    val site: String?,
    val size: Int?,
    val type: String?
)

@JsonClass(generateAdapter = true)
data class GenreResponse(
    val id: Int?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class ImagesResponse(
    val backdrops: List<Backdrop>?,
    val posters: List<Poster>?
)

@JsonClass(generateAdapter = true)
data class Poster(
    val aspect_ratio: Double?,
    val file_path: String?,
    val height: Int?,
    val iso_639_1: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val width: Int?
)

@JsonClass(generateAdapter = true)
data class Backdrop(
    val aspect_ratio: Double?,
    val file_path: String?,
    val height: Int?,
    val iso_639_1: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val width: Int?
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
)

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    val iso_3166_1: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    val iso_639_1: String?,
    val name: String?
)