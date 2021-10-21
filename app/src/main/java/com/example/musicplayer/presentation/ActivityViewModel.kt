package com.example.musicplayer.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel() : ViewModel() {
    val isOnLoginScreen = MutableLiveData(false)
}