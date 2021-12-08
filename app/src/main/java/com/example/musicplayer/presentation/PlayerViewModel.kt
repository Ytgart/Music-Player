package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.db.SongRepository
import com.example.musicplayer.data.remote.SpotifyAPIRepository
import com.example.musicplayer.data.db.Song
import com.example.musicplayer.domain.MusicPlayer
import com.example.musicplayer.domain.PlayerState
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val spotifyAPIRepository: SpotifyAPIRepository,
    private val songRepository: SongRepository,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Song>()
    val currentSongData: LiveData<Song>
        get() = _currentSongData

    val playerState: LiveData<PlayerState>
        get() = musicPlayer.playerState

    fun setCurrentSong(newSong: Song) = _currentSongData.postValue(newSong)

    fun getAllSongs() = songRepository.getSongs()

    fun getFavouriteSongs() = songRepository.getFavouriteSongs()

    fun searchForSongs(queryString: String) = songRepository.findSongs(queryString)

    fun addSongFromSpotifyAPI(id: String, token: String) {
        viewModelScope.launch {
            val songInfo = spotifyAPIRepository.getSong(id, token)
            if (songInfo != null) {
                val newSong = Song(
                    songInfo.album.images[0].url,
                    songInfo.previewURL,
                    songInfo.name,
                    songInfo.artists[0].name,
                    29000
                )
                songRepository.addSong(newSong)
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
}