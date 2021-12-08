package com.example.musicplayer.data.db

import androidx.lifecycle.asLiveData
import com.example.musicplayer.data.db.PlayerDatabase
import com.example.musicplayer.data.db.Song

class SongRepository(private val playerDatabase: PlayerDatabase) {

    fun getSongs() = playerDatabase.playerDBDao().getSongsList().asLiveData()

    fun getFavouriteSongs() = playerDatabase.playerDBDao().getFavouriteSongsList().asLiveData()

    fun findSongs(queryString: String) =
        playerDatabase.playerDBDao().getSearchedSongs(queryString).asLiveData()

    suspend fun addSong(newSong: Song) = playerDatabase.playerDBDao().insertSong(newSong)

    suspend fun addSongs(songsList: List<Song>) =
        playerDatabase.playerDBDao().insertSongs(songsList)

    suspend fun deleteSong(keySong: Song) = playerDatabase.playerDBDao().deleteSong(keySong)

    suspend fun updateSong(keySong: Song) = playerDatabase.playerDBDao().updateSong(keySong)
}