package com.felipeortiz.trailers.ui.videoDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeortiz.trailers.ui.trending.TrendingViewModel
import java.lang.IllegalArgumentException

class VideoDetailVideoModelFactory(private val application: Application, private val movieId: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoDetailViewModel::class.java)) {
            return VideoDetailViewModel(application, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}