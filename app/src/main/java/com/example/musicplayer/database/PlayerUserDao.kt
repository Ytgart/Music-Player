package com.example.musicplayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.musicplayer.database.PlayerUser

@Dao
interface PlayerUserDao {
    @Query("SELECT * FROM PlayerUser")
    fun getAllUsers(): List<PlayerUser>

    @Query("SELECT * FROM PlayerUser WHERE login == (:login)")
    fun getUserByLogin(login: String): PlayerUser?

    @Insert
    fun insert(userData: PlayerUser)
}