package com.example.musicplayer.data

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SpotifyAPIRepository {
    private val moshi = Moshi.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.spotify.com/v1/")
        .build()

    private val requester = retrofit.create(SpotifyAPIService::class.java)

    suspend fun getSong(id: String, token: String): SpotifySongResponseModel {
        return requester.getSingleSong(id, "Bearer $token")
    }
}