package com.example.musicplayer.data

import com.example.musicplayer.data.database.PlayerDatabase
import com.example.musicplayer.data.database.PlayerUser

class PlayerUserRepository(private val database: PlayerDatabase) {

    suspend fun addUser(user: PlayerUser) = database.playerDBDao().insertUser(user)

    suspend fun getUser(login: String) = database.playerDBDao().getUserByLogin(login)
}