package com.example.musicplayer.data.db

import com.example.musicplayer.data.db.PlayerDatabase
import com.example.musicplayer.data.db.PlayerUser

class PlayerUserRepository(private val database: PlayerDatabase) {

    suspend fun addUser(user: PlayerUser) = database.playerDBDao().insertUser(user)

    suspend fun getUser(login: String) = database.playerDBDao().getUserByLogin(login)
}