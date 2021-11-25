package com.example.musicplayer.data

import android.util.Log
import com.example.musicplayer.data.database.Song
import io.ktor.client.call.*
import org.json.JSONObject
import org.json.JSONTokener

class SpotifyAPIRepository(private val spotifyAPIRequester: SpotifyAPIRequester) {

    suspend fun getSongInfo(id: String, token: String): Song {
        val jsonObject =
            JSONObject(JSONTokener(spotifyAPIRequester.requestSong(id, token).receive()))

        val imageURL = jsonObject
            .getJSONObject("album")
            .getJSONArray("images")
            .getJSONObject(0)
            .getString("url")

        val songName = jsonObject.getString("name")

        val performerName = jsonObject
            .getJSONObject("album")
            .getJSONArray("artists")
            .getJSONObject(0)
            .getString("name")

        return Song(imageURL, songName, performerName)
    }
}