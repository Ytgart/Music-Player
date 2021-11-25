package com.example.musicplayer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Song(
    @Json(name = "id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Json(name = "coverURL")
    @ColumnInfo(name = "coverPath")
    val coverURL: String,
    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String,
    @Json(name = "performer")
    @ColumnInfo(name = "performer")
    val performer: String,
    @Json(name = "duration")
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
