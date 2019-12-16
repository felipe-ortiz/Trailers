package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felipeortiz.trailers.models.Movie
import com.felipeortiz.trailers.models.TopRatedMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: Movie)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

}