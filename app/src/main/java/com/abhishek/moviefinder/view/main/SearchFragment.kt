package com.abhishek.moviefinder.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.moviefinder.R
import com.abhishek.moviefinder.databinding.FragmentSearchBinding
import com.abhishek.moviefinder.databinding.ItemMovieBinding
import com.abhishek.moviefinder.view.details.DetailsActivity
import com.abhishek.moviefinder.view.details.EXTRA_ID
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModel: SearchViewModel

    private val disposables = CompositeDisposable()
    private lateinit var binding: FragmentSearchBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(
                inflater,
                R.layout.fragment_search,
                container,
                false
            )
            .apply { viewModel = this@SearchFragment.viewModel }

        disposables += viewModel.getEvents()
            .subscribeBy { handleViewModelEvent(it) }

        return binding.root
    }

    private fun handleViewModelEvent(event: SearchViewModel.Event) {
        when (event) {
            SearchViewModel.Event.OnHideKeyboard ->
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(binding.root.windowToken, 0)
            SearchViewModel.Event.OnError ->
                Snackbar.make(binding.root, getString(R.string.label_error), Snackbar.LENGTH_LONG)
                    .show()
            SearchViewModel.Event.OnNoResult ->
                Snackbar.make(
                        binding.root,
                        getString(R.string.label_no_result),
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            is SearchViewModel.Event.OnMovieClicked -> {
                startActivity(
                    Intent(requireContext(), DetailsActivity::class.java)
                        .apply {
                            putExtra(EXTRA_ID, event.id)
                        }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}

class MovieItemAdapter(
    private val items: ObservableList<SearchItemViewModel>
) : RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class MovieItemViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: SearchItemViewModel) {
            binding.viewModel = item
        }
    }
}