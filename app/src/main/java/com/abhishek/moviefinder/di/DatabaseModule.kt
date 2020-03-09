package com.abhishek.moviefinder.di

import android.content.Context
import androidx.room.Room
import com.abhishek.moviefinder.repository.FavoriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): FavoriteDatabase {
        return Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorites").build()
    }
}