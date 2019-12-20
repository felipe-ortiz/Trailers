package com.felipeortiz.trailers.ui.search

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.di.Injector
import com.felipeortiz.trailers.models.MovieSearchResult
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.item_search.view.*

class SearchFragment : Fragment() {

    // TODO: Change how search works

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val section = Section()
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        groupAdapter.add(section)
        view.recycler_view_search.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.movieSearchResults.observe(this, Observer { movieSearchResults ->
            val items = movieSearchResults.map { searchResult ->
                SearchItem(searchResult, requireContext() , View.OnClickListener {
                    val id = searchResult.id
                    val action = SearchFragmentDirections.actionSearchFragmentToVideoDetailFragment(id)
                    findNavController().navigate(action)
                })
            }
            section.clear()
            section.addAll(items)
        })

        val searchEditText = view.search_bar_edit_text

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchMovies(query)
                } else {
                    section.clear()
                }
            }
        })

        searchEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(this.requireContext(), v)
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = SearchViewModelFactory(Injector.get().movieRepository())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private fun showKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private inner class SearchItem(private val movieSearchResult: MovieSearchResult,
                           private val context: Context,
                           private val onClickListener: View.OnClickListener) : Item<GroupieViewHolder>() {

        override fun getLayout(): Int = R.layout.item_search

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.search_item_title_text_view.text = movieSearchResult.title
            val url = "https://image.tmdb.org/t/p/w500${movieSearchResult.poster_path}"
            val posterImageView = viewHolder.itemView.seach_item_poster_image
            Glide.with(context).load(url).into(posterImageView)
            viewHolder.itemView.setOnClickListener(onClickListener)
        }
    }
}

