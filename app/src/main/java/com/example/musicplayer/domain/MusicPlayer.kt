package com.example.musicplayer.domain

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData

enum class PlayerState {
    NOT_PREPARED,
    PREPARED,
    PLAYING,
    PAUSED,
    ENDED
}

class MusicPlayer {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    val playerState = MutableLiveData(PlayerState.NOT_PREPARED)

    init {
        setup()
    }

    fun prepareSong(url: String?) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }

    fun togglePause(): Boolean {
        return if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            playerState.postValue(PlayerState.PAUSED)
            false
        } else {
            mediaPlayer.start()
            playerState.postValue(PlayerState.PLAYING)
            true
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    fun getCurrentPosition() = mediaPlayer.currentPosition

    fun getFileDuration() = mediaPlayer.duration

    fun resetPlayer() {
        mediaPlayer.reset()
        playerState.postValue(PlayerState.NOT_PREPARED)
    }

    private fun setup() {
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
        mediaPlayer.setOnPreparedListener {
            playerState.postValue(PlayerState.PREPARED)
        }
        mediaPlayer.setOnCompletionListener {
            playerState.postValue(PlayerState.ENDED)
        }
    }
}