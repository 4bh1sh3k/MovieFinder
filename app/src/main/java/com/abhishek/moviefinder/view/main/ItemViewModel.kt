package com.abhishek.moviefinder.view.main

import com.abhishek.moviefinder.repository.MovieLite
import io.reactivex.subjects.Subject

class ItemViewModel(
    val movie: MovieLite,
    private val events: Subject<MainViewModel.Event>
) {
    fun onClick() {
        events.onNext(MainViewModel.Event.OnMovieClicked(movie.id))
    }
}