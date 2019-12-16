package com.felipeortiz.trailers.internal

import androidx.room.TypeConverter
import com.felipeortiz.trailers.models.Trailer
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class TypeConverters {

//    private val gson = Gson()
//
//    @TypeConverter
//    fun jsonToVideos(json: String) : Videos {
//        Timber.d("Testing $json")
//
//        val type = object : TypeToken<Videos>() {}.type
//        return gson.fromJson<Videos>(json, type)
//
//    }
//
//    @TypeConverter
//    fun videosToJson(videos: Videos) : String {
//        val type = object : TypeToken<Videos>() {}.type
//        return gson.toJson(videos, type)
//    }
//
//    @TypeConverter
//    fun jsonToImages(json: String) : ImagesResponse {
//        val type = object : TypeToken<ImagesResponse>() {}.type
//        return gson.fromJson<ImagesResponse>(json, type)
//    }
//
//    @TypeConverter
//    fun imagesToJson(imagesResponse: ImagesResponse) : String {
//        val type = object : TypeToken<ImagesResponse>() {}.type
//        return gson.toJson(imagesResponse, type)
//    }
}

class TypeAdapter {
    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun jsonToTrailers(json: String): List<Trailer> {
        val type = Types.newParameterizedType(List::class.java, Trailer::class.java)
        val adapter: JsonAdapter<List<Trailer>> = moshi.adapter(type)
        val trailers = adapter.fromJson(json)
        return trailers ?: emptyList()
    }

    @TypeConverter
    fun trailersToJson(trailers: List<Trailer>): String {
        val type = Types.newParameterizedType(List::class.java, Trailer::class.java)
        val adapter: JsonAdapter<List<Trailer>> = moshi.adapter(type)
        return adapter.toJson(trailers)
    }

    @TypeConverter
    fun jsonToStrings(json: String): List<String> {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        val strings = adapter.fromJson(json)
        return strings ?: emptyList()
    }

    @TypeConverter
    fun stringsToJson(strings: List<String>): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.toJson(strings)
    }
}
