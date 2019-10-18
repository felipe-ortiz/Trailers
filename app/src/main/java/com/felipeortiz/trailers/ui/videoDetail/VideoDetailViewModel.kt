package com.felipeortiz.trailers.ui.videoDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.felipeortiz.trailers.data.repository.MovieRepository
import com.felipeortiz.trailers.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoDetailViewModel @Inject constructor(private val movieId: Int,
                           private val repository: MovieRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()

    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val selectedMovie: LiveData<Movie> = repository.getMovie(movieId)

    init {
       scope.launch {
           repository.fetchSelectedMovie(movieId)
       }
    }
}
