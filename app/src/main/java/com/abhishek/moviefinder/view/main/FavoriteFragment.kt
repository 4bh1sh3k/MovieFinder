package com.abhishek.moviefinder.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.moviefinder.R
import com.abhishek.moviefinder.databinding.FragmentFavoriteBinding
import com.abhishek.moviefinder.databinding.ItemFavoriteBinding
import com.abhishek.moviefinder.view.details.DetailsActivity
import com.abhishek.moviefinder.view.details.EXTRA_ID
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var viewModel: FavoriteViewModel

    private val disposables = CompositeDisposable()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFavoriteBinding>(
                inflater,
                R.layout.fragment_favorite,
                container,
                false
            )
            .apply { viewModel = this@FavoriteFragment.viewModel }

        disposables += viewModel.getEvents()
            .subscribeBy { handleViewModelEvent(it) }

        return binding.root
    }

    private fun handleViewModelEvent(event: FavoriteViewModel.Event) {
        when (event) {
            is FavoriteViewModel.Event.OnMovieClicked -> {
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

class FavoriteItemAdapter(
    private val items: ObservableList<FavoriteItemViewModel>
) : RecyclerView.Adapter<FavoriteItemAdapter.FavoriteItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemFavoriteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite,
            parent,
            false
        )
        return FavoriteItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class FavoriteItemViewHolder(
        private val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: FavoriteItemViewModel) {
            binding.viewModel = item
        }
    }
}