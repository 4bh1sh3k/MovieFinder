package com.abhishek.moviefinder.di

import com.abhishek.moviefinder.repository.FavoriteDatabase
import com.abhishek.moviefinder.repository.OmdbApi
import com.abhishek.moviefinder.repository.OmdbRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideOmdbApi(retrofit: Retrofit): OmdbApi {
        return retrofit.create(OmdbApi::class.java)
    }

    @Provides
    fun provideOmdbRepository(api: OmdbApi, database: FavoriteDatabase): OmdbRepository {
        return OmdbRepository(api, database)
    }
}