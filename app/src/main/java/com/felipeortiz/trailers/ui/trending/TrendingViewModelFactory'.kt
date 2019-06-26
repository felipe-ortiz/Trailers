package com.felipeortiz.trailers.ui.trending

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeortiz.trailers.data.network.response.MovieCache
import com.felipeortiz.trailers.data.network.response.MoviesDataSource
import java.lang.IllegalArgumentException

class TrendingViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingViewModel::class.java)) {
            return TrendingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}