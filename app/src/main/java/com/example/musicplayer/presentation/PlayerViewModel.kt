package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.JsonDataRepository
import com.example.musicplayer.domain.Song

class PlayerViewModel(private val jsonDataRepository: JsonDataRepository) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    private val _songList = MutableLiveData<List<Song>>()
    val songList: LiveData<List<Song>>
        get() = _songList

    init {
        loadSongs()
    }

    fun setCurrentSong(newSong: Song) = _currentSongData.postValue(newSong)

    private fun loadSongs() {
        _songList.postValue(jsonDataRepository.getSongs())
    }
}