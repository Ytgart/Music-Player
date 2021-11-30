package com.example.musicplayer.di

import com.example.musicplayer.data.SpotifyAPIRepository
import org.koin.dsl.module

val spotifyAPIRepositoryModule = module {
    single { SpotifyAPIRepository() }
}