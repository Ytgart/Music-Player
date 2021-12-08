package com.example.musicplayer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerUser::class, Song::class], exportSchema = false, version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDBDao(): PlayerDBDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PlayerDatabase::class.java,
            "main-database"
        )
            .build()
    }
}