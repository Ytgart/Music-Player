package com.example.musicplayer.presentation

import androidx.lifecycle.*
import com.example.musicplayer.data.SongRepository
import com.example.musicplayer.data.SpotifyAPIRepository
import com.example.musicplayer.data.database.PlayerDatabase
import com.example.musicplayer.data.database.Song
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val spotifyAPIRepository: SpotifyAPIRepository,
    private val songRepository: SongRepository
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    private val _currentMenuItem = MutableLiveData<Int>()
    val currentMenuItem: LiveData<Int>
        get() = _currentMenuItem

    fun setCurrentSong(newSong: Song) = _currentSongData.postValue(newSong)

    fun getAllSongs() = songRepository.getSongs()

    fun getFavouriteSongs() = songRepository.getFavouriteSongs()

    fun searchForSongs(queryString: String) = songRepository.findSongs(queryString)

    fun addSong(id: String, token: String) {
        viewModelScope.launch {
            val songInfo = spotifyAPIRepository.getSongInfo(id, token)
            if (songInfo != null) {
                songRepository.addSong(songInfo)
            }
        }
    }

    fun addAlbum(id: String, token: String) {
        viewModelScope.launch {
            val songsInfo = spotifyAPIRepository.getSongsInfo(id, token)
            if (songsInfo != null) {
                songRepository.addSongs(songsInfo)
            }
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            songRepository.deleteSong(song)
        }
    }

    fun updateSong(newSongInfo: Song) {
        viewModelScope.launch {
            songRepository.updateSong(newSongInfo)
        }
    }

    fun setCurrentMenuItem(id: Int) = _currentMenuItem.postValue(id)
}