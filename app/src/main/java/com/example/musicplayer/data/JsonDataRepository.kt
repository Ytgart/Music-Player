package com.example.musicplayer.data

import android.content.Context
import com.example.musicplayer.data.database.Song
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class JsonDataRepository(val context: Context) {
    private lateinit var moshiAdapter: JsonAdapter<List<Song>>

    init {
        buildMoshi()
    }

    private fun buildMoshi() {
        val moshi = Moshi.Builder().build()
        val songDataList: Type = Types.newParameterizedType(List::class.java, Song::class.java)
        moshiAdapter = moshi.adapter(songDataList)
    }

    fun getSongs(): List<Song> {
        return moshiAdapter.fromJson(context.assets.open("test.json").bufferedReader()
            .use { it.readText() })!!
    }
}