package com.example.musicplayer.data

import android.util.Log
import com.example.musicplayer.data.database.Song
import io.ktor.client.call.*
import io.ktor.client.features.*
import org.json.JSONObject
import org.json.JSONTokener

class SpotifyAPIRepository(private val spotifyAPIRequester: SpotifyAPIRequester) {

    suspend fun getSongInfo(id: String, token: String): Song? {
        val jsonObject: JSONObject
        try {
            jsonObject =
                JSONObject(JSONTokener(spotifyAPIRequester.requestSong(id, token).receive()))
        } catch (e: ClientRequestException) {
            return null
        }

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

        val duration = jsonObject.getInt("duration_ms")

        return Song(imageURL, songName, performerName, duration)
    }

    suspend fun getSongsInfo(id: String, token: String): List<Song>? {

        val songs: MutableList<Song> = mutableListOf()

        val songsJSONObject: JSONObject
        try {
            songsJSONObject =
                JSONObject(JSONTokener(spotifyAPIRequester.requestSongs(id, token).receive()))
        } catch (e: ClientRequestException) {
            return null
        }

        val albumJsonObject: JSONObject
        try {
            albumJsonObject =
                JSONObject(JSONTokener(spotifyAPIRequester.requestAlbum(id, token).receive()))
        } catch (e: ClientRequestException) {
            return null
        }

        val songsArray = songsJSONObject.getJSONArray("items")

        for (i in 0 until songsJSONObject.getInt("total")) {
            val imageURL = albumJsonObject
                .getJSONArray("images")
                .getJSONObject(0)
                .getString("url")

            val songName = songsArray.getJSONObject(i).getString("name")

            val performerName = songsArray.getJSONObject(i)
                .getJSONArray("artists")
                .getJSONObject(0)
                .getString("name")

            val duration = songsArray.getJSONObject(i).getInt("duration_ms")

            songs.add(Song(imageURL, songName, performerName, duration))
        }
        return songs
    }
}