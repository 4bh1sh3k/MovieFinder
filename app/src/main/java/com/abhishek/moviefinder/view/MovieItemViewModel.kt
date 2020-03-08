package com.abhishek.moviefinder.view

import com.abhishek.moviefinder.repository.MovieLite
import io.reactivex.subjects.Subject

class MovieItemViewModel(
    val movie: MovieLite,
    private val events: Subject<MainViewModel.Event>
) {
    fun onClick() {
        events.onNext(MainViewModel.Event.OnMovieClicked(movie.id))
    }
}