package com.example.musicplayer.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.domain.Song

class PlayerViewModel : ViewModel() {
    val currentSongData = MutableLiveData<Song>()
}