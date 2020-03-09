package com.abhishek.moviefinder

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.moviefinder.view.main.FavoriteItemAdapter
import com.abhishek.moviefinder.view.main.FavoriteItemViewModel
import com.abhishek.moviefinder.view.main.MovieItemAdapter
import com.abhishek.moviefinder.view.main.SearchItemViewModel
import com.bumptech.glide.Glide

@BindingAdapter("isVisible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .into(view)
}

@BindingAdapter("movieItems")
fun setMovieItems(view: RecyclerView, items: ObservableList<SearchItemViewModel>) {
    view.adapter = MovieItemAdapter(items)
}

@BindingAdapter("favoriteItems")
fun setFavoriteItems(view: RecyclerView, items: ObservableList<FavoriteItemViewModel>) {
    view.adapter = FavoriteItemAdapter(items)
}