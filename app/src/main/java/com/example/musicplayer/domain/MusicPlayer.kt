package com.example.musicplayer.domain

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData

class MusicPlayer {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    val isPrepared = MutableLiveData(false)

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
            false
        } else {
            mediaPlayer.start()
            true
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    fun getCurrentPosition() = mediaPlayer.currentPosition

    fun getFileDuration() = mediaPlayer.duration

    fun resetPlayer() = mediaPlayer.reset()

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
            isPrepared.postValue(true)
        }

        mediaPlayer.setOnCompletionListener {
            isPrepared.postValue(false)
        }
    }
}