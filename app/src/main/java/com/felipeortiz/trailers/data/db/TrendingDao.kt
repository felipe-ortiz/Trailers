package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felipeortiz.trailers.data.db.entity.TrendingMovie

@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: TrendingMovie)

    @Query("SELECT * FROM trending_movies")
    fun getTrendingMovies(): LiveData<List<TrendingMovie>>
}