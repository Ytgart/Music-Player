package com.example.musicplayer.di

import com.example.musicplayer.data.database.PlayerDatabase
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        PlayerDatabase.build(get())
    }
}