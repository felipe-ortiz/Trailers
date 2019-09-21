package com.felipeortiz.trailers.ui.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.felipeortiz.trailers.data.network.response.TrendingMovie
import com.felipeortiz.trailers.data.repository.MovieRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = SupervisorJob()
    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)


    val trendingMovies: LiveData<List<TrendingMovie>> = repository.trendingMovies

    init {
        scope.launch {
            repository.getTrendingMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
