package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.domain.interfaces.ISpotifyAPIRepository
import com.example.musicplayer.domain.interfaces.ITrackDBEntityRepository

class TrackUseCase(
    private val trackDBEntityRepository: ITrackDBEntityRepository,
    private val spotifyAPIRepository: ISpotifyAPIRepository
) {
    fun getAllTracks() = trackDBEntityRepository.getTracks()

    fun getFavouriteTracks() = trackDBEntityRepository.getFavouriteTracks()

    fun getTracksByFilter(queryString: String) =
        trackDBEntityRepository.getTracksByFilter(queryString)

    suspend fun addTrackFromAPI(id: String, token: String) {
        val newTrack = spotifyAPIRepository.getTrack(id, token)
        if (newTrack != null) {
            trackDBEntityRepository.addTrack(newTrack)
        }
    }

    suspend fun deleteTrack(track: Track) = trackDBEntityRepository.deleteTrack(track)

    suspend fun updateTrack(track: Track) = trackDBEntityRepository.updateTrack(track)
}