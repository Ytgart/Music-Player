package com.example.musicplayer.data.preferences

import android.content.Context

class LoginStateRepository(context: Context) {
    private val sharedPref = context.getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE)

    fun saveLoginState(newState: Boolean) {
        val editor = sharedPref.edit()

        editor.putBoolean("isLogged", newState)
        editor.apply()
    }

    fun isLogged() = sharedPref.getBoolean("isLogged", false)
}