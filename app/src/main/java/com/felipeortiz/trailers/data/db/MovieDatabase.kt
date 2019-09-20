package com.felipeortiz.trailers.data.db

import android.content.Context
import androidx.room.*
import com.felipeortiz.trailers.data.db.entity.DatabaseTrendingMovie
import com.felipeortiz.trailers.models.Movie

@Database(
    entities = [Movie::class, DatabaseTrendingMovie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.felipeortiz.trailers.internal.TypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val trendingMoviesDao: TrendingMoviesDao
}

@Volatile private var INSTANCE : MovieDatabase? = null
fun getDatabase(context: Context): MovieDatabase {
    val tempInstance = INSTANCE
    if (tempInstance != null) {
        return tempInstance
    }

    synchronized(MovieDatabase::class.java) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database")
            .fallbackToDestructiveMigration()
            .build()

        INSTANCE = instance

        return instance
    }
}