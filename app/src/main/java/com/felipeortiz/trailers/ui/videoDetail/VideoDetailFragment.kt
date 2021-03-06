package com.felipeortiz.trailers.ui.videoDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.fragment_video_detail.*
import timber.log.Timber
import android.content.Context
import android.widget.ImageView
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.di.Injector
import com.felipeortiz.trailers.internal.doOnApplyWindowInsets
import com.felipeortiz.trailers.models.Trailer
import com.felipeortiz.trailers.ui.OnItemClickHandler
import com.felipeortiz.trailers.ui.OnItemLongClickHandler
import kotlinx.android.synthetic.main.fragment_video_detail.view.*
import kotlinx.android.synthetic.main.item_trailer_thumbnail.view.*
import java.text.NumberFormat
import java.util.*


class VideoDetailFragment : Fragment(), OnItemClickHandler, OnItemLongClickHandler {

    companion object {
        fun newInstance() = VideoDetailFragment()
    }

    private lateinit var viewModelFactory: VideoDetailVideoModelFactory
    private lateinit var viewModel: VideoDetailViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrailersAdapter
    private val trailers = mutableListOf<Trailer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)
        view.nested_scroll_view.doOnApplyWindowInsets { scrollView, windowInsets, initialPadding ->
            scrollView.updatePadding(
                bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom
            )
        }

        recyclerView = view.trailers_recyclerview
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        this.adapter = TrailersAdapter(this, this, requireContext())
        recyclerView.adapter = this.adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoDetailViewModel::class.java)

        viewModel.selectedMovie.observe(this, Observer {
            it?.let {
                title_detail_text_view.text = it.title
                overview_detail_text_view.text = it.overview
                runtime_detail_text_view.text = getString(R.string.minutes, it.runtime)
                rating_text_view.text = it.vote_average.toString()
                year_text_view.text = it.release_date
                language_text_view.text = getTheMovieDbLanguage(it.original_language)
                budget_text_view.text = getString(R.string.budget_formatted,
                    NumberFormat.getNumberInstance(Locale.US).format(it.budget))
                tagline_text_view.text = it.tagline
                if (tagline_text_view.text.isEmpty())
                    tagline_text_view.visibility = View.GONE
                val imageUrl = "https://image.tmdb.org/t/p/w500" + it.poster_path
                Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_movies).into(poster_image)

                this.adapter.setTrailersMovies(it.trailers)

                it.trailers.forEach { trailer ->
                    if (trailer.site == "YouTube") {
                        trailers.add(trailer)
                    }
                    Timber.d("Video name:${trailer.name}, Key: ${trailer.key}")
                }
            }
        })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("movieId")?.let {
            Timber.d("Movie id = $it")
            viewModelFactory = VideoDetailVideoModelFactory(it, Injector.get().movieRepository())
        }
    }

    override fun onItemClick(position: Int) {
        val videoKey = trailers[position].key
        val action = VideoDetailFragmentDirections.actionVideoDetailFragmentToPlayVideoFragment(videoKey)
        this.findNavController().navigate(action)
    }

    override fun onLongClickItem(position: Int) {
        Toast.makeText(this.context, trailers[position].name, Toast.LENGTH_SHORT).show()
    }

    private fun getTheMovieDbLanguage(languageCode: String) : String {
        return when (languageCode) {
            "en" -> "English"
            "es" -> "Spanish"
            "de" -> "German"
            "pt" -> "Portuguese"
            "ja" -> "Japanese"
            else -> "Unavailable"
        }
    }
}

class TrailersAdapter(private val onItemClickHandler: OnItemClickHandler,
                      private val onItemLongClickHandler: OnItemLongClickHandler,
                      private val context: Context) :
        RecyclerView.Adapter<TrailersAdapter.TrailersHolder>() {

    private var trailers = emptyList<Trailer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer_thumbnail, parent, false)
        return TrailersHolder(view)
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: TrailersHolder, position: Int) {
        val imageUrl = "https://img.youtube.com/vi/${trailers[position].key}/hqdefault.jpg"
        holder.title.text = trailers[position].name
        Glide.with(context).load(imageUrl).error(R.drawable.ic_play_circle_filled_black_24dp).into(holder.thumbnail)
    }

    fun setTrailersMovies(trailers: List<Trailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }


    inner class TrailersHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener,
        View.OnLongClickListener {

        val title: TextView = view.video_title
        val thumbnail: ImageView = view.thumbnail_image_view

        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickHandler.onItemClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            onItemLongClickHandler.onLongClickItem(adapterPosition)
            return true
        }
    }
}
