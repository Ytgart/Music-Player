package com.example.musicplayer.di

import com.example.musicplayer.data.preferences.LoginStateRepository
import org.koin.dsl.module

val loginStateModule = module {
    single {
        LoginStateRepository(get())
    }
}