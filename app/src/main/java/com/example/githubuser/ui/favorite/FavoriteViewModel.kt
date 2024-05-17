package com.example.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.FavoriteUserRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase?

    init {
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteuserDao()
    }

    fun getfavoriteuser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getfavoriteuser()
    }
}