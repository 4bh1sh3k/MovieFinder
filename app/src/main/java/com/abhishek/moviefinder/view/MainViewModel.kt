package com.abhishek.moviefinder.view

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.abhishek.moviefinder.repository.MovieLite
import com.abhishek.moviefinder.repository.OmdbRepository
import com.abhishek.moviefinder.repository.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: OmdbRepository
) : DefaultLifecycleObserver {
    private val disposables = CompositeDisposable()

    private val events = PublishSubject.create<Event>()
    fun getEvents() = events.hide()

    val loading = ObservableBoolean()
    val items = ObservableArrayList<MovieItemViewModel>()

    init {
        loading.set(true)
        disposables += repository.searchMovie("Back to the future")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { handleResult(it) }
    }

    private fun handleResult(result: Result<List<MovieLite>>) {
        loading.set(false)
        if (result.result == null) {
            //TODO Show error
        } else {
            items.clear()
            items.addAll(result.result.map { MovieItemViewModel(it, events) })
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        disposables.clear()
    }

    sealed class Event {
        object OnError: Event()
        class OnMovieClicked(val id: String): Event()
    }
}