package com.example.musicplayer.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.data.entities.TrackDBEntity
import com.example.musicplayer.domain.entities.Track

fun List<TrackDBEntity>.toTrackList(): LiveData<List<Track>> {
    return MutableLiveData(
        this.map {
            Track(
                it.id,
                it.coverURL,
                it.previewURL,
                it.name,
                it.performer,
                it.duration,
                it.isFavorite
            )
        }
    )
}