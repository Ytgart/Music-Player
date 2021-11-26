package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.SpotifyAPIRepository
import com.example.musicplayer.data.database.PlayerDatabase
import com.example.musicplayer.data.database.Song
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val spotifyAPIRepository: SpotifyAPIRepository,
    val playerDatabase: PlayerDatabase
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    private val _currentMenuItem = MutableLiveData<Int>()
    val currentMenuItem: LiveData<Int>
        get() = _currentMenuItem

    private val _searchResults = MutableLiveData<List<Song>>()
    val searchResults: LiveData<List<Song>>
        get() = _searchResults

    fun setCurrentSong(newSong: Song) = _currentSongData.postValue(newSong)

    fun addSong(id: String, token: String) {
        viewModelScope.launch {
            val songInfo = spotifyAPIRepository.getSongInfo(id, token)
            if (songInfo != null) {
                playerDatabase.playerDBDao().insertSong(songInfo)
            }
        }
    }

    fun addAlbum(id: String, token: String) {
        viewModelScope.launch {
            val songsInfo = spotifyAPIRepository.getSongsInfo(id, token)
            if (songsInfo != null) {
                playerDatabase.playerDBDao().insertSongs(songsInfo)
            }
        }
    }

    fun searchForSongs(name: String) {
        viewModelScope.launch {
            val results = playerDatabase.playerDBDao().getSearchedSongs(name)
            _searchResults.postValue(results)
        }
    }

    fun clearSearchResults() {
        _searchResults.postValue(listOf())
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            playerDatabase.playerDBDao().deleteSong(song)
        }
    }

    fun updateSong(newSongInfo: Song) {
        viewModelScope.launch {
            playerDatabase.playerDBDao().updateSong(newSongInfo)
        }
    }

    fun setCurrentMenuItem(id: Int) = _currentMenuItem.postValue(id)
}