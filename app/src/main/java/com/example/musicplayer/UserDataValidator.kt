package com.example.musicplayer

import android.text.TextUtils
import com.example.musicplayer.LoginManager.*

class UserDataValidator {

    fun validate(data: UserData, repeatedPassword: String): Int {
        if (!validateLogin(data.login)) return 3
        if (!validateEmail(data.email)) return 2
        if (!validatePassword(data.password, repeatedPassword)) return 1
        return 0
    }

    private fun validateLogin(data: String): Boolean {
        return !TextUtils.isEmpty(data)
    }

    private fun validateEmail(data: String): Boolean {
        return (!TextUtils.isEmpty(data)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches())
    }

    private fun validatePassword(password: String, repeatedPassword: String): Boolean {
        return (password == repeatedPassword && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
            repeatedPassword
        ))
    }
}