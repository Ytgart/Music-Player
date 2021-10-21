package com.example.musicplayer.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    val buttonState = MutableLiveData(false)
}