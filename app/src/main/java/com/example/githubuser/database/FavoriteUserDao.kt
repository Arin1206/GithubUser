package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getfavoriteuser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id =:id ")
    suspend fun checkuser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id=:id")
    suspend fun remove(id: Int): Int

}