package com.example.musicplayer.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDBDao {
    @Query("SELECT * FROM Song")
    fun getSongsList(): LiveData<List<Song>>

    @Query("SELECT * FROM Song WHERE isFavorite == 1")
    fun getFavouriteSongsList(): LiveData<List<Song>>

    @Query("SELECT * FROM Song WHERE name LIKE (:name) OR performer LIKE (:name)")
    suspend fun getSearchedSongs(name: String): List<Song>

    @Query("SELECT * FROM PlayerUser WHERE login == (:login)")
    suspend fun getUserByLogin(login: String): PlayerUser

    @Insert
    suspend fun insertUser(userData: PlayerUser)

    @Insert
    suspend fun insertSong(song: Song)

    @Insert
    suspend fun insertSongs(songs: List<Song>)

    @Delete
    suspend fun deleteSong(song: Song)

    @Update
    suspend fun updateSong(song: Song)
}