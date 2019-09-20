package com.felipeortiz.trailers.ui.trending

import com.felipeortiz.trailers.data.network.response.TrendingMovie

interface OnMovieClickListener {
    fun onItemClick(movie: TrendingMovie)
}