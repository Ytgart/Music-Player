package com.example.musicplayer.utils

import java.util.concurrent.TimeUnit

class TimeConverter(private val timeMillis: Int) {

    fun toDigitalClockFormatString(): String {
        val durationMinutes =
            TimeUnit.MILLISECONDS.toMinutes(timeMillis.toLong()) % 60
        val durationSeconds =
            TimeUnit.MILLISECONDS.toSeconds(timeMillis.toLong()) % 60

        return String.format("%d:%02d", durationMinutes, durationSeconds)
    }
}