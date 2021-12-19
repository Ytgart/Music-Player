package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.domain.usecases.TrackUseCase
import com.example.musicplayer.utils.MusicPlayer
import com.example.musicplayer.utils.PlayerState
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val trackUseCase: TrackUseCase,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private val _currentTrackData = MutableLiveData<Track>()
    val currentTrackData: LiveData<Track>
        get() = _currentTrackData

    val playerState: LiveData<PlayerState>
        get() = musicPlayer.playerState

    fun setCurrentTrack(trackID: Int) {
        viewModelScope.launch {
            musicPlayer.resetPlayer()
            val track = trackUseCase.getTrackByID(trackID)
            _currentTrackData.postValue(track)
            musicPlayer.prepareSong(track.previewURL)
        }
    }

    fun getAllTracks() = trackUseCase.getAllTracks()

    fun getFavouriteTracks() = trackUseCase.getFavouriteTracks()

    fun searchForTracks(queryString: String) = trackUseCase.getTracksByFilter(queryString)

    fun addSongFromSpotifyAPI(queryString: String) {
        viewModelScope.launch {
            trackUseCase.addTrackFromAPI(queryString)
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

    fun seekInSong(position: Int) {
        musicPlayer.seekTo(position)
    }

    fun togglePause() = musicPlayer.togglePause()

    fun getPlayerPosition() = musicPlayer.getCurrentPosition()

    fun getSongDuration() = musicPlayer.getFileDuration()

    fun isTrackPlaying() = musicPlayer.isPlaying()

    fun provideSpotifyToken(token: String) = trackUseCase.provideToken(token)
}