package com.example.musicplayer.di

import com.example.musicplayer.data.SongRepository
import org.koin.dsl.module

val songRepositoryModule = module {
    single { SongRepository(get()) }
}