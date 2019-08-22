package com.felipeortiz.trailers.ui.videoDetail

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.felipeortiz.trailers.R
import kotlinx.android.synthetic.main.fragment_video_detail.*
import kotlinx.android.synthetic.main.fragment_video_detail_parallax.*
import timber.log.Timber

class VideoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = VideoDetailFragment()
    }

    private lateinit var viewModelFactory: VideoDetailVideoModelFactory
    private lateinit var viewModel: VideoDetailViewModel
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoDetailViewModel::class.java)
        viewModel.selectedMovie.observe(this, Observer {
            it?.let {
                title_detail_text_view.text = it.title
                overview_detail_text_view.text = it.overview
                runtime_detail_text_view.text = getString(R.string.runtime, it.runtime)
            }
        })
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireNotNull(this.activity).application
        arguments?.getInt("movieId")?.let {
            Timber.d("Movie id = $it")
            viewModelFactory = VideoDetailVideoModelFactory(application, it)
        }
    }
}
