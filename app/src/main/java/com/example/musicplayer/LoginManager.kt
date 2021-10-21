package com.example.musicplayer

import android.content.Context
import com.example.musicplayer.data.database.PlayerUser

class LoginManager(context: Context) {
    private val sharedPref = context.getSharedPreferences("LOGIN_DATA1", Context.MODE_PRIVATE)

    fun isLogged() = loadLoginState()

    fun saveLoginState(newState: Boolean) {
        val editor = sharedPref.edit()

        editor.putBoolean("isLogged", newState)
        editor.apply()
    }

    private fun loadLoginState() = sharedPref.getBoolean("isLogged", false)

    fun checkLoginData(login: String, password: String, userData: PlayerUser?): Boolean {
        return if (userData != null) (login == userData.login && password == userData.password)
        else false
    }
}