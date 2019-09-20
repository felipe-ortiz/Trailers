package com.felipeortiz.trailers.internal

import androidx.room.TypeConverter
import com.felipeortiz.trailers.data.network.response.Images
import com.felipeortiz.trailers.data.network.response.Videos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

class TypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun jsonToVideos(json: String) : Videos {
        Timber.d("Testing $json")

        val type = object : TypeToken<Videos>() {}.type
        return gson.fromJson<Videos>(json, type)

    }

    @TypeConverter
    fun videosToJson(videos: Videos) : String {
        val type = object : TypeToken<Videos>() {}.type
        return gson.toJson(videos, type)
    }

    @TypeConverter
    fun jsonToImages(json: String) : Images {
        val type = object : TypeToken<Images>() {}.type
        return gson.fromJson<Images>(json, type)
    }

    @TypeConverter
    fun imagesToJson(images: Images) : String {
        val type = object : TypeToken<Images>() {}.type
        return gson.toJson(images, type)
    }

//    @TypeConverter
//    fun jsonToBackdrops(json: String) : List<Backdrop> {
//        val type = object : TypeToken<List<Backdrop>>() {}.type
//        return gson.fromJson<List<Backdrop>>(json, type)
//    }
//
//    @TypeConverter
//    fun backdropsToJson(backdrops: List<Backdrop>) : String {
//        val type = object : TypeToken<List<Backdrop>>() {}.type
//        return gson.toJson(backdrops, type)
//    }
//
//    @TypeConverter
//    fun jsonToPosters(json: String) : List<Poster> {
//        val type = object : TypeToken<List<Poster>>() {}.type
//        return gson.fromJson(json, type)
//    }
//
//    @TypeConverter
//    fun postersToJson(posters: List<Poster>) : String {
//        val type = object : TypeToken<List<Poster>>() {}.type
//        return gson.toJson(posters, type)
//    }
}