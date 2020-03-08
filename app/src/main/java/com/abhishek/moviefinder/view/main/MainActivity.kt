package com.abhishek.moviefinder.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.moviefinder.R
import com.abhishek.moviefinder.databinding.ActivityMainBinding
import com.abhishek.moviefinder.databinding.ItemMovieBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val disposables = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply { viewModel = this@MainActivity.viewModel }

        disposables += viewModel.getEvents()
            .subscribeBy { handleViewModelEvent(it) }
    }

    private fun handleViewModelEvent(event: MainViewModel.Event) {
        when (event) {
            MainViewModel.Event.OnHideKeyboard ->
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(binding.root.windowToken, 0)
            MainViewModel.Event.OnError ->
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
                    .show()
            is MainViewModel.Event.OnMovieClicked -> {
                Snackbar.make(binding.root, "Clicked a movie", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}

class MovieItemAdapter(
    private val items: ObservableList<ItemViewModel>
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
        fun bindView(item: ItemViewModel) {
            binding.viewModel = item
        }
    }
}
