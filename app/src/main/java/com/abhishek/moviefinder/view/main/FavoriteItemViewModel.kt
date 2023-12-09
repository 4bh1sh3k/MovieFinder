package com.abhishek.moviefinder.view.main

import com.abhishek.moviefinder.repository.MovieLite

class FavoriteItemViewModel(
    val movie: MovieLite,
    private val onMovieClick: (String) -> Unit
) {
    fun onClick() {
        onMovieClick(movie.id)
    }
}