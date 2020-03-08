package com.abhishek.moviefinder.view.main

import com.abhishek.moviefinder.repository.MovieLite
import com.abhishek.moviefinder.repository.MovieLiteExt
import io.reactivex.subjects.Subject

class ItemViewModel(
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