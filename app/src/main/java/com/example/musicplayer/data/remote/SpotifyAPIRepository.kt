package com.example.musicplayer.data.remote

import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.domain.interfaces.ISpotifyAPIRepository
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SpotifyAPIRepository : ISpotifyAPIRepository {
    private val moshi = Moshi.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.spotify.com/v1/")
        .build()

    private val requester = retrofit.create(SpotifyAPIService::class.java)

    override suspend fun getTrack(id: String, token: String): Track? {
        return try {
            requester.getSingleSong(id, "Bearer $token").toTrack()
        } catch (exception: HttpException) {
            null
        }
    }
}