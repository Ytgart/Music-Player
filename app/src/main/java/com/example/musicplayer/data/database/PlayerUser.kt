package com.example.musicplayer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerUser(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
) {
    constructor(login: String, email: String, password: String) : this(0, login, email, password)
}
