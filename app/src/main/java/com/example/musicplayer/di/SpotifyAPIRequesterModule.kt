package com.example.musicplayer.di

import com.example.musicplayer.data.SpotifyAPIRequester
import org.koin.dsl.module

val spotifyAPIRequesterModule = module {
    single { SpotifyAPIRequester() }
}