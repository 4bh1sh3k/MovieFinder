package com.abhishek.moviefinder.di

import com.abhishek.moviefinder.MovieFinderApplication
import com.abhishek.moviefinder.view.ActivitiesModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent {
    fun inject(application: MovieFinderApplication)
}