package com.abhishek.moviefinder.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieLite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}