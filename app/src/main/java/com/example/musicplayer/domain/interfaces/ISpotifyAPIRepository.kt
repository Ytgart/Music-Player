package com.example.musicplayer.domain.interfaces

import com.example.musicplayer.domain.entities.Track

interface ISpotifyAPIRepository {
    suspend fun getTrack(id: String, token: String): Track?
}