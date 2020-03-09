package com.abhishek.moviefinder.view

import com.abhishek.moviefinder.view.details.DetailsActivity
import com.abhishek.moviefinder.view.main.FavoriteFragment
import com.abhishek.moviefinder.view.main.MainActivity
import com.abhishek.moviefinder.view.main.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface UiModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    fun contributeDetailsActivity(): DetailsActivity
}