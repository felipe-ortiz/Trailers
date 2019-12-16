package com.felipeortiz.trailers.data.db

import androidx.room.*
import com.felipeortiz.trailers.data.db.entity.DatabaseTrendingMovie
import com.felipeortiz.trailers.internal.TypeAdapter
import com.felipeortiz.trailers.models.*

@Database(
    entities = [
        Movie::class,
        DatabaseTrendingMovie::class,
        DiscoverMovie::class,
        TopRatedMovie::class,
        NowPlayingMovie::class,
        UpcomingMovie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeAdapter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun trendingMoviesDao(): TrendingMoviesDao
    abstract fun discoverMoviesDao(): DiscoverMoviesDao
}
