package com.abhishek.moviefinder.view.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.abhishek.moviefinder.repository.Movie
import com.abhishek.moviefinder.repository.OmdbRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: OmdbRepository
) : DefaultLifecycleObserver {

    val loading = ObservableBoolean(false)
    val movie = ObservableField<Movie>()

    private val disposables = CompositeDisposable()

    private val events = PublishSubject.create<Event>()
    fun getEvents() = events.hide()

    fun showDetailsById(id: String?) {
        if (id != null) {
            loading.set(true)
            disposables += repository.getMovieDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    loading.set(false)
                    handleResult(it.result)
                }
        } else {
            events.onNext(Event.OnError)
        }
    }

    private fun handleResult(result: Movie?) {
        if (result != null) {
            movie.set(result)
        } else {
            events.onNext(Event.OnError)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        disposables.clear()
    }

    sealed class Event {
        object OnError : Event()
    }
}