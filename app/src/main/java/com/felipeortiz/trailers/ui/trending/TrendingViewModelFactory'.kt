package com.felipeortiz.trailers.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeortiz.trailers.data.repository.MovieRepository
import java.lang.IllegalArgumentException

class TrendingViewModelFactory(
    private val repository: MovieRepository
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingViewModel::class.java)) {
            return TrendingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}