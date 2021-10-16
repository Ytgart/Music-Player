package com.example.musicplayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerUser::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerUserDao(): PlayerUserDao
}