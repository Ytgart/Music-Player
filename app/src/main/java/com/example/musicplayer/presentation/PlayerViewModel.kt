package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.utils.MusicPlayer
import com.example.musicplayer.utils.PlayerState
import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.domain.usecases.TrackUseCase
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val trackUseCase: TrackUseCase,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private val _currentSongData = MutableLiveData<Track>()
    val currentTrackDBEntityData: LiveData<Track>
        get() = _currentSongData

    val playerState: LiveData<PlayerState>
        get() = musicPlayer.playerState

    fun setCurrentSong(newTrack: Track) =
        _currentSongData.postValue(newTrack)

    fun getAllTracks() = trackUseCase.getAllTracks()

    fun getFavouriteTracks() = trackUseCase.getFavouriteTracks()

    fun searchForTracks(queryString: String) = trackUseCase.getTracksByFilter(queryString)

    fun addSongFromSpotifyAPI(id: String, token: String) {
        viewModelScope.launch {
            trackUseCase.addTrackFromAPI(id, token)
        }
    }

    fun deleteSong(track: Track) {
        viewModelScope.launch {
            trackUseCase.deleteTrack(track)
        }
    }

    fun updateSong(newTrackInfo: Track) {
        viewModelScope.launch {
            trackUseCase.updateTrack(newTrackInfo)
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