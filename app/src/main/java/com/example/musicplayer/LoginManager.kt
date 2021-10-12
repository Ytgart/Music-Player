package com.example.musicplayer

import android.content.Context
import java.io.Serializable

class LoginManager(context: Context) {

    data class UserData(
        var login: String,
        var email: String,
        var password: String
    ) : Serializable

    private val userDataManager = UserDataManager(context)

    fun registerUser(data: UserData) {
        userDataManager.saveData(data)
        userDataManager.saveLoginState(true)
    }

    fun loginUser() {
        userDataManager.saveLoginState(true)
    }

    fun unloginUser() {
        userDataManager.saveLoginState(false)
    }

    fun checkLoginData(login: String, password: String): Boolean {
        val tempUserData = userDataManager.loadData()
        return (login == tempUserData.login && password == tempUserData.password)
    }

    fun isLogged() = userDataManager.loadLoginState()
}