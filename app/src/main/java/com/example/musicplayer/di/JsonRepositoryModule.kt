package com.example.musicplayer.di

import com.example.musicplayer.data.JsonDataRepository
import org.koin.dsl.module

val jsonRepositoryModule = module {
    single { JsonDataRepository(get()) }
}