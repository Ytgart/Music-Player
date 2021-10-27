package com.example.musicplayer.data

import android.content.Context
import com.example.musicplayer.domain.Song
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class JsonDataRepository(val context: Context) {
    var songs: List<Song> = listOf()
        private set

    fun loadData() {
        val moshi = Moshi.Builder().build()
        val songDataList: Type = Types.newParameterizedType(List::class.java, Song::class.java)
        val adapter: JsonAdapter<List<Song>> = moshi.adapter(songDataList)

        songs = adapter.fromJson(context.assets.open("test.json").bufferedReader()
            .use { it.readText() })!!
    }
}