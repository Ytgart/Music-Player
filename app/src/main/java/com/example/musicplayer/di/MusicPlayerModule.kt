package com.example.musicplayer.di

import com.example.musicplayer.domain.MusicPlayer
import org.koin.dsl.module

var musicPlayerModule = module {
    single { MusicPlayer() }
}