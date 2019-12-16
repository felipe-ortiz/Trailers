package com.felipeortiz.trailers.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.felipeortiz.trailers.data.repository.MovieRepository
import com.felipeortiz.trailers.models.DiscoverMovie
import com.felipeortiz.trailers.models.NowPlayingMovie
import com.felipeortiz.trailers.models.TopRatedMovie
import com.felipeortiz.trailers.models.UpcomingMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val discoverMovies: LiveData<List<DiscoverMovie>> = repository.discoverMovies
    val topRatedMovies: LiveData<List<TopRatedMovie>> = repository.topRatedMovies
    val nowPlayingMovies: LiveData<List<NowPlayingMovie>> = repository.nowPlayingMovies
    val upcomingMovies: LiveData<List<UpcomingMovie>> = repository.upcomingMovies


    init {
        scope.launch {
            repository.refreshPopularMovies()
            repository.refreshTopRatedMovies()
            repository.refreshNowPlayingMovies()
            repository.refreshUpcomingMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
