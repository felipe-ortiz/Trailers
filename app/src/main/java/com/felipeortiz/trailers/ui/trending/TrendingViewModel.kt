package com.felipeortiz.trailers.ui.trending

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.felipeortiz.trailers.data.db.MovieDatabase
import com.felipeortiz.trailers.data.db.entity.TrendingMovie
import com.felipeortiz.trailers.data.db.getDatabase
import com.felipeortiz.trailers.data.repository.MovieRepository2
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TrendingViewModel(application: Application) : AndroidViewModel(application) {

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

    private val database = getDatabase(application)
    private val repository = MovieRepository2(database)

    val trendingMovies: LiveData<List<TrendingMovie>> = repository.trendingMovies

    init {
        scope.launch {
            repository.refreshMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
