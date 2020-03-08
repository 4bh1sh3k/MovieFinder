package com.abhishek.moviefinder.repository

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "eaf7e2dc"

interface OmdbApi {
    @GET("?type=movie&apikey=$API_KEY")
    fun searchMovie(@Query("s") query: String): Single<SearchResponse>

    @GET("?apikey=$API_KEY")
    fun getMovieDetails(@Query("i") id: String): Single<Movie>
}