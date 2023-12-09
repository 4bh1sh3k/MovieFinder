package com.abhishek.moviefinder.view.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.abhishek.moviefinder.repository.FavoriteDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    database: FavoriteDatabase
) : DefaultLifecycleObserver {

    private val events = PublishSubject.create<Event>()
    fun getEvents() = events.hide()

    private val disposables = CompositeDisposable()
    val items = ObservableArrayList<FavoriteItemViewModel>()

    init {
        disposables += database.favoriteDao().getFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                items.clear()
                items.addAll(it.map { movieLite ->
                    FavoriteItemViewModel(
                        movieLite,
                        ::onMovieClick
                    )
                })
            }
    }

    private fun onMovieClick(id: String) {
        events.onNext(Event.OnMovieClicked(id))
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        disposables.clear()
    }

    sealed class Event {
        class OnMovieClicked(val id: String) : Event()
    }
}