package com.example.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1, exportSchema = false
)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteuserDao(): FavoriteUserDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "favoriteuser_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }


    }
}