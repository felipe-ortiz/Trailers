package com.felipeortiz.trailers.data.network.response

import com.felipeortiz.trailers.models.Movie

data class MovieResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val images: Images,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Double,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val videos: Videos,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toModelMovie() : Movie {
        return Movie(
            adult = this.adult,
            backdrop_path = this.backdrop_path,
            budget = this.budget,
            homepage = this.homepage,
            id = this.id,
            images = this.images,
            imdb_id = this.imdb_id,
            original_language = this.original_language,
            original_title = this.original_title,
            overview = this.overview,
            popularity = this.popularity,
            poster_path = this.poster_path,
            release_date = this.release_date,
            revenue = this.revenue,
            runtime = this.runtime,
            status = this.status,
            tagline = this.tagline,
            title = this.title,
            video = this.video,
            videos = this.videos,
            vote_average = this.vote_average,
            vote_count = this.vote_count
        )
    }
}

data class Videos(
    val results: List<Result>
)

data class Result(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class Images(
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
)

data class Poster(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)

data class Backdrop(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)

//fun Videos.toVideoList(videoObj: Videos) : List<Video> {
//    val videos = arrayListOf<Video>()
//    videoObj.results.forEach {
//        val vid = Video(
//            id = it.id,
//            language = it.iso_3166_1,
//            productionCountry = it.iso_639_1,
//            key = it.key,
//            name = it.name,
//            site = it.site,
//            size = it.size,
//            type = it.type
//        )
//        videos.add(vid)
//    }
//
//    return videos
//}
