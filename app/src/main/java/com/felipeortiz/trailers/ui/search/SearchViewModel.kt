package com.felipeortiz.trailers.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.felipeortiz.trailers.data.repository.MovieRepository
import com.felipeortiz.trailers.models.MovieSearchResult
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _searchResults = MutableLiveData<List<MovieSearchResult>>()
    val movieSearchResults: LiveData<List<MovieSearchResult>> = _searchResults

    fun searchMovies(query: String) {
        val input = query.toLowerCase(Locale.getDefault()).trim()
        if (input.isEmpty()) {
            _searchResults.value = emptyList()
        } else {
            scope.launch {
                val movies = repository.searchMovies(input)
                _searchResults.value = movies
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
