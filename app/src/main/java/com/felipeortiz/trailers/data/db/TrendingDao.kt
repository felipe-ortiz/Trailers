package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felipeortiz.trailers.data.db.entity.TrendingResult

@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(trendingResult: TrendingResult)

    @Query("select * from trending_results")
    fun getTrendingResults(): LiveData<List<TrendingResult>>
}