package com.felipeortiz.trailers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.felipeortiz.trailers.data.db.entity.TrendingResult

@Database(
    entities = [TrendingResult::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun trendingDao() : TrendingDao

    companion object {
        @Volatile private var instance : MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MovieDatabase::class.java, "movie.db")
                .build()

    }
}