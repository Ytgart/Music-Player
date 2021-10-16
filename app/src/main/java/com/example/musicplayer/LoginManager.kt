package com.example.musicplayer

import android.content.Context
import com.example.musicplayer.database.PlayerUser

class LoginManager(context: Context) {

    private val userDataManager = UserDataManager(context)

    fun loginUser() {
        userDataManager.saveLoginState(true)
    }

    fun unloginUser() {
        userDataManager.saveLoginState(false)
    }

    fun checkLoginData(login: String, password: String, userData: PlayerUser?): Boolean {
        return if (userData != null) (login == userData.login && password == userData.password)
        else false
    }

    fun isLogged() = userDataManager.loadLoginState()
}