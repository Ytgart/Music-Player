package com.example.musicplayer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "coverPath")
    val coverURL: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "performer")
    val performer: String,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @Transient
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) {
    constructor(coverURL: String, name: String, performer: String, duration: Int) : this(
        0,
        coverURL,
        name,
        performer,
        duration,
        false
    )
}
