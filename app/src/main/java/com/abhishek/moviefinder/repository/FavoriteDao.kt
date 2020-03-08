package com.abhishek.moviefinder.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getFavorites(): Observable<List<MovieLite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MovieLite): Completable
}