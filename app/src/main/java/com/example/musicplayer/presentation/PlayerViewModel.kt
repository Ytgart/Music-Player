package com.example.musicplayer.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.domain.Song

class PlayerViewModel : ViewModel() {
    val songData = MutableLiveData<Song>()
    val currentSong = MutableLiveData(1)

    fun changeSongData(data: Song) {
        songData.postValue(data)
    }
}