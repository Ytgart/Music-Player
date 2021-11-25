package com.example.musicplayer.data

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class SpotifyAPIRequester {
    private var client: HttpClient = HttpClient()

    suspend fun requestSong(id: String, token: String): HttpResponse {
        return client.get("https://api.spotify.com/v1/tracks/$id") {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $token"
                )
            }
        }
    }

    suspend fun requestSongs(id: String, token: String): HttpResponse {
        return client.get("https://api.spotify.com/v1/albums/$id/tracks") {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $token"
                )
            }
        }
    }

    suspend fun requestAlbum(id: String, token: String): HttpResponse {
        return client.get("https://api.spotify.com/v1/albums/$id") {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $token"
                )
            }
        }
    }
}