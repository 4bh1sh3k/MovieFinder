package com.abhishek.moviefinder.repository

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("")
    fun searchMovie(@Query("s") query: String): Single<SearchResponse>

    @GET("")
    fun getMovieDetails(@Query("i") id: String): Single<Movie>
}