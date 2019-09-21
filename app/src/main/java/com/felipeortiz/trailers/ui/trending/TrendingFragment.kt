package com.felipeortiz.trailers.ui.trending

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felipeortiz.trailers.MovieApplication
import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.data.network.response.TrendingMovie
import com.felipeortiz.trailers.di.Injector
import kotlinx.android.synthetic.main.trending_fragment.view.*
import kotlinx.android.synthetic.main.trending_list_view.view.*

private const val POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/w92"

class TrendingFragment : Fragment(), OnMovieClickListener {

    companion object {
        fun newInstance() = TrendingFragment()
    }

    private lateinit var viewModel: TrendingViewModel
    private lateinit var viewModelFactory: TrendingViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrendingMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.trending_fragment, container, false)

        adapter = TrendingMovieAdapter(requireContext(), this)
        recyclerView = view.recycler_view_trending
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = this.adapter
        viewModel.trendingMovies.observe(this, Observer { trendingMovies ->
            trendingMovies?.let {
                adapter.setTrendingMovies(it)
            }
        })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = TrendingViewModelFactory(Injector.get().movieRepository())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrendingViewModel::class.java)
    }

    override fun onItemClick(movie: TrendingMovie) {
        val action = TrendingFragmentDirections.actionTrendingFragmentToVideoDetailFragment(movie.id)
        this.findNavController().navigate(action)
    }
}

class TrendingMovieAdapter(private val context: Context, private val onMovieClickHandler: OnMovieClickListener) :
    RecyclerView.Adapter<TrendingMovieAdapter.TrendingMovieViewHolder>()
{
    private var trendingMovies = emptyList<TrendingMovie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_list_view, parent, false)
        return TrendingMovieViewHolder(view)
    }

    override fun getItemCount(): Int = trendingMovies.size

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        val imageUrl = POSTER_PATH_BASE_URL + trendingMovies[position].poster_path
        Glide.with(context)
            .load(imageUrl)
            .into(holder.posterImageView)
        holder.titleTextView.text = when {
            trendingMovies[position].title != null -> trendingMovies[position].title
            trendingMovies[position].name != null -> trendingMovies[position].name
            trendingMovies[position].original_name != null -> trendingMovies[position].original_name
            trendingMovies[position].original_title != null -> trendingMovies[position].original_title
            else -> "Unavailable"
        }
        holder.rating.text = trendingMovies[position].vote_average.toString()
    }

    internal fun setTrendingMovies(trendingMovies: List<TrendingMovie>) {
        this.trendingMovies = trendingMovies
        notifyDataSetChanged()
    }
    inner class TrendingMovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleTextView: TextView = itemView.title_trending_text_view
        val posterImageView: ImageView = itemView.poster_trending
        val rating: TextView = itemView.rating_trending_text_view

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val selectedMovie = trendingMovies[adapterPosition]
            onMovieClickHandler.onItemClick(selectedMovie)
        }
    }
}


