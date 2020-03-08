package com.abhishek.moviefinder.di

import com.abhishek.moviefinder.MovieFinderApplication
import com.abhishek.moviefinder.view.ActivitiesModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent {
    fun inject(application: MovieFinderApplication)
}