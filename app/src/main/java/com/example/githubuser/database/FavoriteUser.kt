package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    var id: Int,
    var username: String,
    var avatarurl: String
) : Parcelable
