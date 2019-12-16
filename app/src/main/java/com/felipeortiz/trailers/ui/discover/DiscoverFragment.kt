package com.felipeortiz.trailers.ui.discover

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.di.Injector
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_discover.view.*
import kotlinx.android.synthetic.main.item_carousel.view.*
import kotlinx.android.synthetic.main.item_discover_movie.view.*
import kotlinx.android.synthetic.main.item_header.view.*

class DiscoverFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var viewModelFactory: DiscoverViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discover, container, false)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()

        view.discover_recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(this@DiscoverFragment.context, LinearLayoutManager.VERTICAL, false)
        }

        view.search_edit_text.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        val popularMoviesSection = Section()
        viewModel.discoverMovies.observe(this, Observer { discoverMovies ->
            val items = discoverMovies.map { movie ->
                MovieItem(movie.title, movie.poster_path, requireContext(), View.OnClickListener {
                    val action = DiscoverFragmentDirections.discoverToDetail(movie.id)
                    this.findNavController().navigate(action)
                })
            }
            popularMoviesSection.clear()
            popularMoviesSection.addAll(items)
        })

        val topRatedMoviesSection = Section()
        viewModel.topRatedMovies.observe(this, Observer { topRatedMovies ->
            val items = topRatedMovies.map { movie ->
                MovieItem(movie.title, movie.poster_path, requireContext(), View.OnClickListener {
                    val action = DiscoverFragmentDirections.discoverToDetail(movie.id)
                    this.findNavController().navigate(action)
                })
            }
            topRatedMoviesSection.clear()
            topRatedMoviesSection.addAll(items)
        })

        val nowPlayingMoviesSection = Section()
        viewModel.nowPlayingMovies.observe(this, Observer { nowPlayingMovies ->
            val items = nowPlayingMovies.map { movie ->
                MovieItem(movie.title, movie.poster_path, requireContext(), View.OnClickListener {
                    val action = DiscoverFragmentDirections.discoverToDetail(movie.id)
                    this.findNavController().navigate(action)
                })
            }
            nowPlayingMoviesSection.clear()
            nowPlayingMoviesSection.addAll(items)
        })

        val upcomingMoviesSection = Section()
        viewModel.upcomingMovies.observe(this, Observer { upcomingMovies ->
            val items = upcomingMovies.map { movie ->
                MovieItem(movie.title, movie.poster_path, requireContext(), View.OnClickListener {
                    val action = DiscoverFragmentDirections.discoverToDetail(movie.id)
                    this.findNavController().navigate(action)
                })
            }
            upcomingMoviesSection.clear()
            upcomingMoviesSection.addAll(items)
        })

        val popularCarouselSection = makeCarouselSection("Most popular movies", popularMoviesSection)
        val topRatedCarouselSection = makeCarouselSection("Top rated movies", topRatedMoviesSection)
        val nowPlayingCarouselSection = makeCarouselSection("Now playing", nowPlayingMoviesSection)
        val upcomingCarouselSection = makeCarouselSection("Upcoming movies", upcomingMoviesSection)

        groupAdapter.add(popularCarouselSection)
        groupAdapter.add(topRatedCarouselSection)
        groupAdapter.add(nowPlayingCarouselSection)
        groupAdapter.add(upcomingCarouselSection)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DiscoverViewModelFactory(Injector.get().movieRepository())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoverViewModel::class.java)
    }

    private fun makeCarouselSection(headerTitle: String, itemSection: Section) : Section {
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(itemSection)
        val carouselItem = CarouselItem(adapter)
        val carouselSection = Section()
        carouselSection.setHeader(HeaderItem(headerTitle))
        carouselSection.add(carouselItem)
        return carouselSection
    }

}

private class HeaderItem(private val title: String) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.header_text_view.text = title
    }

    override fun getLayout(): Int = R.layout.item_header

}

private class MovieItem(private val movieTitle: String, private val posterPath: String, private val context: Context,
                        private val onItemClickListener: View.OnClickListener) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val url = "https://image.tmdb.org/t/p/w500$posterPath"
        val imageView = viewHolder.itemView.item_discover_image_view
        Glide.with(context).load(url).placeholder(R.drawable.ic_movies).into(imageView)
        viewHolder.itemView.setOnClickListener(onItemClickListener)
    }

    override fun getLayout(): Int = R.layout.item_discover_movie
}

private class CarouselItem(private val carouselAdapter: GroupAdapter<GroupieViewHolder>) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carousel_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = carouselAdapter
        }
    }

    override fun getLayout(): Int = R.layout.item_carousel

}