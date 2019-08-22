package com.felipeortiz.trailers.ui.videoDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.getDatabase
import com.felipeortiz.trailers.data.repository.MovieRepository
import com.felipeortiz.trailers.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class VideoDetailViewModel(application: Application, movieId: Int) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()

    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val repository = MovieRepository(database)
    val selectedMovie: LiveData<Movie> = repository.getMovie(movieId)

    init {
       scope.launch {
           repository.fetchSelectedMovie(movieId)
       }
    }
}
