package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.SongRepository
import com.example.musicplayer.data.SpotifyAPIRepository
import com.example.musicplayer.data.database.Song
import com.example.musicplayer.domain.MusicPlayer
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val spotifyAPIRepository: SpotifyAPIRepository,
    private val songRepository: SongRepository,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    private val _currentMenuItem = MutableLiveData<Int>()
    val currentMenuItem: LiveData<Int>
        get() = _currentMenuItem

    val isSongPrepared: LiveData<Boolean>
        get() = musicPlayer.isPrepared

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

    fun prepareSong() = musicPlayer.prepareSong(_currentSongData.value?.previewURL)

    fun seekInSong(position: Int) {
        musicPlayer.seekTo(position)
    }

    fun togglePause(): Boolean = musicPlayer.togglePause()

    fun getPlayerPosition() = musicPlayer.getCurrentPosition()

    fun getSongDuration() = musicPlayer.getFileDuration()

    fun resetPlayer() = musicPlayer.resetPlayer()

    fun setCurrentMenuItem(id: Int) = _currentMenuItem.postValue(id)
}