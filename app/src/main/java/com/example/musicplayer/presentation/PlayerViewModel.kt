package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.JsonDataRepository
import com.example.musicplayer.data.SpotifyAPIRepository
import com.example.musicplayer.data.database.PlayerDatabase
import com.example.musicplayer.data.database.Song
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val jsonDataRepository: JsonDataRepository,
    private val spotifyAPIRepository: SpotifyAPIRepository,
    val playerDatabase: PlayerDatabase
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    private val _currentMenuItem = MutableLiveData<Int>()
    val currentMenuItem: LiveData<Int>
        get() = _currentMenuItem

    fun setCurrentSong(newSong: Song) = _currentSongData.postValue(newSong)

    fun addSong(id: String) {
        viewModelScope.launch {
            playerDatabase.playerDBDao().insertSong(spotifyAPIRepository.getSongInfo(id))
        }
    }

    fun updateSong(newSongInfo: Song) {
        viewModelScope.launch {
            playerDatabase.playerDBDao().updateSong(newSongInfo)
        }
    }

    fun setCurrentMenuItem(id: Int) = _currentMenuItem.postValue(id)
}