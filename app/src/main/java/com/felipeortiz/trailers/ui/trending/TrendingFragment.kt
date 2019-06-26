package com.felipeortiz.trailers.ui.trending

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.data.db.entity.TrendingMovie
import com.felipeortiz.trailers.data.network.ConnectivityInterceptorImpl
import com.felipeortiz.trailers.data.network.MovieDbService
import com.felipeortiz.trailers.data.network.response.MovieCache
import timber.log.Timber

class TrendingFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingFragment()
    }

    private lateinit var viewModel: TrendingViewModel
    private lateinit var viewModelFactory: TrendingViewModelFactory
    private lateinit var trendingMovies: List<TrendingMovie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.trending_fragment, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrendingViewModel::class.java)

        viewModel.trendingMovies.observe(this, Observer { trendingResults ->
            trendingResults?.let {
                Timber.d("Trending Results NOT null")
                for (result in it) {
                    Timber.d("Title: ${result.title}")
                }
            }
        })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        viewModelFactory = TrendingViewModelFactory(application)
    }
}
