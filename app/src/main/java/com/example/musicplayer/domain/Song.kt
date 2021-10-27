package com.example.musicplayer.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Song(
    @Json(name = "coverPath")
    val coverPath: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "performer")
    val performer: String,
    @Json(name = "duration")
    val duration: Int
)

