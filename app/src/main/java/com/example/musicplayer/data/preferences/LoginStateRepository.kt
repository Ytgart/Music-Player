package com.example.musicplayer.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.musicplayer.domain.interfaces.ILoginStateRepository

class LoginStateRepository(context: Context) : ILoginStateRepository {
    private val sharedPref = context.getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE)

    override fun saveLoginState(newState: Boolean) {
        editPreferences {
            it.putBoolean("isLogged", newState)
        }
    }

    override fun getLoginState() = sharedPref.getBoolean("isLogged", false)

    private fun editPreferences(actions: (editor: SharedPreferences.Editor) -> Unit) {
        sharedPref.edit().apply {
            actions.invoke(this)
            apply()
        }
    }
}