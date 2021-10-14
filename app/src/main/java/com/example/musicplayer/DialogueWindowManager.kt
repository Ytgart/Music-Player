package com.example.musicplayer

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object DialogueWindowManager {
    fun showAlert(message: String, context: Context) {
        val toast = Toast.makeText(context,message,Toast.LENGTH_SHORT)
        toast.show()
    }
}