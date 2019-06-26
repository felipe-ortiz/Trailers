package com.felipeortiz.trailers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.felipeortiz.trailers.data.db.entity.TrendingMovie

@Database(
    entities = [TrendingMovie::class],
    version = 3,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val trendingDao: TrendingDao
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