package com.abhishek.moviefinder.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "favorite")
data class MovieLite(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "imdbID") @PrimaryKey val id: String,
    @Json(name = "Type") val type: String,
    @Json(name = "Poster") val poster: String
)

class MovieLiteExt(
    val movieLite: MovieLite,
    val isFavorite: Boolean
)

@JsonClass(generateAdapter = true)
class SearchResponse(
    @Json(name = "Search") val results: List<MovieLite>
)

@JsonClass(generateAdapter = true)
class Movie(
    @Json(name = "Title") val title: String,
    @Json(name = "Director") val director: String,
    @Json(name = "Year") val year: String,
    @Json(name = "Plot") val summary: String,
    @Json(name = "Poster") val poster: String
)

/**
 * Generic result wrapper
 * @param result Result of the method. Set to null if method fails
 */
class Result<T>(val result: T? = null)