package com.felipeortiz.trailers.data.db

import android.content.Context
import androidx.room.*
import com.felipeortiz.trailers.data.db.entity.DatabaseTrendingMovie
import com.felipeortiz.trailers.models.Movie
import dagger.Provides
import javax.inject.Singleton

@Database(
    entities = [
        Movie::class,
        DatabaseTrendingMovie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.felipeortiz.trailers.internal.TypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun trendingMoviesDao(): TrendingMoviesDao
}
