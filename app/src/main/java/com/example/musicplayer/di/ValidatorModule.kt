package com.example.musicplayer.di

import com.example.musicplayer.helpers.UserDataValidator
import org.koin.dsl.module

val validatorModule = module {
    single {
        UserDataValidator()
    }
}