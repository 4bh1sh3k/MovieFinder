package com.abhishek.moviefinder.view

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}