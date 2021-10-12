package com.example.musicplayer

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object DialogueWindowManager {
    fun showAlert(message: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Предупрежедние")
            .setMessage(message)
            .setPositiveButton("Окей") { _: DialogInterface, _: Int -> }
            .show()
    }
}