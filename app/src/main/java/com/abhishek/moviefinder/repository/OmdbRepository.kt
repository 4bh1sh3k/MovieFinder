package com.abhishek.moviefinder.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class OmdbRepository(private val api: OmdbApi) {
    fun searchMovie(query: String): Single<Result<List<MovieLite>>> {
        return api.searchMovie(query)
            .map { Result(it.results) }
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