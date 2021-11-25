package com.example.musicplayer.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class SpotifyAPIRequester {
    private var client: HttpClient = HttpClient()

    suspend fun requestSong(id: String): HttpResponse {
        return client.get("https://api.spotify.com/v1/tracks/${id}") {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer BQANj4xX7TgZ_VjGxjyZQvuUdTH7U6QwdyZrRVG5ptlHHMB4jMKvsTxYMn_QLUgJzqhFBzCsAyoyyQ8XuHyFmh6oDNnq1wHTxAIR8D3g1GjDAl6AFb8fBPL1hB76l7B5POyncWKGR0lHxE7SaQ55w5efgd9IaUEJhDM"
                )
            }
        }
    }
}