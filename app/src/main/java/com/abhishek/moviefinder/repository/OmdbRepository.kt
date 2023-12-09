package com.abhishek.moviefinder.repository

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class OmdbRepository(
    private val api: OmdbApi,
    private val database: FavoriteDatabase
) {
    fun searchMovie(query: String): Observable<Result<List<MovieLiteExt>>> {
        return Observable.combineLatest(
            api.searchMovie(query).toObservable(),
            database.favoriteDao().getFavorites()
        ) { apiResult: SearchResponse, favorites: List<MovieLite> ->
            apiResult.results.map {
                MovieLiteExt(it, favorites.any { favorite -> favorite.id == it.id })
            }
        }
            .map { Result(it) }
            .onErrorReturnItem(Result(null))
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetails(id: String): Single<Result<Movie>> {
        return api.getMovieDetails(id)
            .map { Result(it) }
            .onErrorReturnItem(Result(null))
            .subscribeOn(Schedulers.io())
    }
}