package com.example.musicplayer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDBDao {
    @Query("SELECT * FROM PlayerUser")
    fun getAllUsers(): List<PlayerUser>

    @Query("SELECT * FROM PlayerUser WHERE login == (:login)")
    suspend fun getUserByLogin(login: String): PlayerUser

    @Insert
    suspend fun insert(userData: PlayerUser)
}