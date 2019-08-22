package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felipeortiz.trailers.data.db.entity.DatabaseMovie
import com.felipeortiz.trailers.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: DatabaseMovie)

    @Query("select * from movies where id = :movieId")
    fun getMovie(movieId: Int): LiveData<DatabaseMovie>

    @Query("select * from movies")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

}