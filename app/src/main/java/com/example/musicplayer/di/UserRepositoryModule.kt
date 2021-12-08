package com.example.musicplayer.di

import com.example.musicplayer.data.db.PlayerUserRepository
import org.koin.dsl.module

val userRepositoryModule = module {
    single {
        PlayerUserRepository(get())
    }
}