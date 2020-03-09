package com.abhishek.moviefinder

import com.abhishek.moviefinder.repository.FavoriteDatabase
import com.abhishek.moviefinder.repository.OmdbApi
import com.abhishek.moviefinder.repository.OmdbRepository
import com.abhishek.moviefinder.repository.SearchResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OmdbRepositoryTest {
    @get:Rule
    val rule = RxSchedulerRule()

    @RelaxedMockK
    lateinit var api: OmdbApi

    @RelaxedMockK
    lateinit var database: FavoriteDatabase

    lateinit var repository: OmdbRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = OmdbRepository(api, database)
    }

    @Test
    fun when_apiFails_then_returnError() {
        every { api.searchMovie(any()) } returns Single.error(Throwable())
        every { database.favoriteDao().getFavorites() } returns Observable.just(listOf(itemThree))

        val observer = repository.searchMovie("test").test()

        observer.assertValue { it.result == null }
    }

    @Test
    fun when_apiReturnsNoResult_then_returnSuccess() {
        every { api.searchMovie(any()) } returns Single.just(SearchResponse(emptyList()))
        every { database.favoriteDao().getFavorites() } returns Observable.just(listOf(itemThree))

        val observer = repository.searchMovie("test").test()

        observer.assertValue { it.result != null && it.result?.isEmpty() == true }
    }

    @Test
    fun when_apiReturnsSuccess_withFavorite_then_returnSuccess() {
        every { api.searchMovie(any()) } returns Single.just(
            SearchResponse(
                listOf(
                    itemOne,
                    itemTwo,
                    itemThree
                )
            )
        )
        every { database.favoriteDao().getFavorites() } returns Observable.just(listOf(itemThree))

        val observer = repository.searchMovie("test").test()

        observer.assertValue { it.result != null && it.result?.count { it.isFavorite } == 1 }
    }
}
