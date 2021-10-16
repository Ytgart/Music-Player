package com.example.musicplayer

import android.content.Context

class UserDataManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("LOGIN_DATA1", Context.MODE_PRIVATE)

    fun saveLoginState(newState: Boolean) {
        val editor = sharedPref.edit()

        editor.putBoolean("isLogged", newState)
        editor.apply()
    }

    fun loadLoginState() = sharedPref.getBoolean("isLogged", false)
}