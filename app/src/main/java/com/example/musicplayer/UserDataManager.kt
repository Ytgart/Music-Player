package com.example.musicplayer

import android.content.Context
import com.example.musicplayer.LoginManager.UserData
import com.google.gson.Gson

class UserDataManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("LOGIN_DATA1", Context.MODE_PRIVATE)
    private val gsonObject = Gson()

    fun saveData(data: UserData) {
        val editor = sharedPref.edit()

        editor.putString("UserData", gsonObject.toJson(data))
        editor.apply()
    }

    fun loadData(): UserData {
        val jsonString = sharedPref.getString("UserData", "")

        if (jsonString != null) {
            return gsonObject.fromJson(jsonString, UserData::class.java)
        }
        return UserData("","","")
    }

    fun saveLoginState(newState: Boolean) {
        val editor = sharedPref.edit()

        editor.putBoolean("isLogged", newState)
        editor.apply()
    }

    fun loadLoginState() = sharedPref.getBoolean("isLogged", false)
}