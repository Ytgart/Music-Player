package com.example.musicplayer

import android.app.Application
import com.example.musicplayer.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.net.URL

class PlayerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PlayerApp)
            modules(
                listOf(
                    dataBaseModule,
                    loginStateModule,
                    loginVMModule,
                    validatorModule,
                    userRepositoryModule,
                    jsonRepositoryModule,
                    playerVMModule,
                    spotifyAPIRepositoryModule,
                    spotifyAPIRequesterModule
                )
            )
        }
    }
}