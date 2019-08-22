package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felipeortiz.trailers.data.db.entity.DatabaseTrendingMovie
import com.felipeortiz.trailers.models.Movie

@Dao
interface TrendingMoviesDao {

    @Query("SELECT * FROM trending_movies")
    fun getTrendingMovies(): LiveData<List<DatabaseTrendingMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg trendingMovies: DatabaseTrendingMovie)

    @Query("DELETE FROM trending_movies")
    fun deleteAll()
}