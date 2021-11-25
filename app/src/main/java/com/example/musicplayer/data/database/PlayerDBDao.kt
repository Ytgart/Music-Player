package com.example.musicplayer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDBDao {
    @Query("SELECT * FROM Song")
    fun getSongsList(): LiveData<List<Song>>

    @Query("SELECT * FROM Song WHERE isFavorite == 1")
    fun getFavouriteSongsList(): LiveData<List<Song>>

    @Query("SELECT * FROM PlayerUser WHERE login == (:login)")
    suspend fun getUserByLogin(login: String): PlayerUser

    @Insert
    suspend fun insertUser(userData: PlayerUser)

    @Insert
    suspend fun insertSong(song: Song)

    @Insert
    suspend fun insertSongs(songs: List<Song>)

    @Update
    suspend fun updateSong(song: Song)
}