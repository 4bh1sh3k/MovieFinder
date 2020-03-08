package com.abhishek.moviefinder.view

import com.abhishek.moviefinder.view.details.DetailsActivity
import com.abhishek.moviefinder.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeDetailsActivity(): DetailsActivity
}