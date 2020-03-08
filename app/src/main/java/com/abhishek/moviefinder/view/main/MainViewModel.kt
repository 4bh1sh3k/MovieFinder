package com.abhishek.moviefinder.view.main

import android.view.inputmethod.EditorInfo
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.abhishek.moviefinder.repository.MovieLite
import com.abhishek.moviefinder.repository.OmdbRepository
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

    val loading = ObservableBoolean(false)
    val items = ObservableArrayList<ItemViewModel>()

    fun onSearch(query: CharSequence, actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            events.onNext(Event.OnHideKeyboard)
            performSearch(query.toString())
            return true
        }
        return false
    }

    private fun performSearch(query: String) {
        items.clear()
        loading.set(true)
        disposables += repository.searchMovie(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                loading.set(false)
                handleResult(it.result)
            }
    }

    private fun handleResult(result: List<MovieLite>?) {
        when {
            result == null -> events.onNext(Event.OnError)
            result.isEmpty() -> events.onNext(Event.OnNoResult)
            else -> items.addAll(result.map { ItemViewModel(it, events) })
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        disposables.clear()
    }

    sealed class Event {
        object OnHideKeyboard : Event()
        object OnError : Event()
        object OnNoResult : Event()
        class OnMovieClicked(val id: String) : Event()
    }
}