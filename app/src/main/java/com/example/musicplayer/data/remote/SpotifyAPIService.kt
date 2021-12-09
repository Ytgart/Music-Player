package com.example.musicplayer.data.remote

import com.example.musicplayer.data.entities.SpotifySongResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyAPIService {
    @GET("tracks/{id}")
    suspend fun getSingleSong(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SpotifySongResponseModel
}