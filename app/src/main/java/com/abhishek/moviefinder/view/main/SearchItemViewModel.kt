package com.abhishek.moviefinder.view.main

import com.abhishek.moviefinder.repository.MovieLite
import com.abhishek.moviefinder.repository.MovieLiteExt

class SearchItemViewModel(
    val movie: MovieLiteExt,
    private val onAddFavorite: (MovieLite) -> Unit,
    private val onMovieClick: (String) -> Unit
) {
    fun onAddClick() {
        onAddFavorite(movie.movieLite)
    }

    fun onClick() {
        onMovieClick(movie.movieLite.id)
    }
}