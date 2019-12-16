package com.felipeortiz.trailers.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felipeortiz.trailers.models.DiscoverMovie
import com.felipeortiz.trailers.models.NowPlayingMovie
import com.felipeortiz.trailers.models.TopRatedMovie
import com.felipeortiz.trailers.models.UpcomingMovie

@Dao
interface DiscoverMoviesDao {

    // Discover movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg discoverMovies: DiscoverMovie)

    @Query("select * from discover_movies")
    fun getAllDiscoverMovies(): LiveData<List<DiscoverMovie>>

    @Query("DELETE FROM discover_movies")
    fun deleteDiscoverMovies()

    // Top rated movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedMovies(vararg topRatedMovies: TopRatedMovie)

    @Query("SELECT * FROM top_rated_movies")
    fun getTopRatedMovies(): LiveData<List<TopRatedMovie>>

    @Query("DELETE FROM top_rated_movies")
    fun deleteTopRatedMovies()

    // Now playing movies
    // Upcoming movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(vararg nowPlayingMovies: NowPlayingMovie)

    @Query("SELECT * FROM now_playing_movies")
    fun getNowPlayingMovies(): LiveData<List<NowPlayingMovie>>

    @Query("DELETE FROM now_playing_movies")
    fun deleteNowPlayingMovies()

    // Upcoming movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingMovies(vararg upcomingMovies: UpcomingMovie)

    @Query("SELECT * FROM upcoming_movies")
    fun getUpcomingMovies(): LiveData<List<UpcomingMovie>>

    @Query("DELETE FROM upcoming_movies")
    fun deleteUpcomingMovies()



}