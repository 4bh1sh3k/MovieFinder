package com.abhishek.moviefinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
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

@BindingAdapter("items")
fun setItems(view: RecyclerView, items: ObservableList<MovieItemViewModel>) {
    view.adapter = MainActivity.MovieItemAdapter(items)
}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        disposables += viewModel.getEvents()
            .subscribeBy {
                when(it) {
                    MainViewModel.Event.OnError ->
                        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
                    is MainViewModel.Event.OnMovieClicked -> {
                        Snackbar.make(binding.root, "Clicked a movie", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
    }

    class MovieItemAdapter(
        private val items: ObservableList<MovieItemViewModel>
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
            fun bindView(item: MovieItemViewModel) {
                binding.viewModel = item
            }
        }
    }
}
