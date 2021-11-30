package com.example.musicplayer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDBDao {
    @Query("SELECT * FROM Song")
    fun getSongsList(): Flow<List<Song>>

    @Query("SELECT * FROM Song WHERE isFavorite == 1")
    fun getFavouriteSongsList(): Flow<List<Song>>

    @Query("SELECT * FROM Song WHERE name LIKE '%' || :name || '%' OR performer LIKE '%' || :name || '%'")
    fun getSearchedSongs(name: String): Flow<List<Song>>

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