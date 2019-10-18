package com.felipeortiz.trailers.ui.videoDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeortiz.trailers.data.repository.MovieRepository
import java.lang.IllegalArgumentException

class VideoDetailVideoModelFactory(private val movieId: Int,
                                   private val repository: MovieRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoDetailViewModel::class.java)) {
            return VideoDetailViewModel(movieId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}