package com.example.musicplayer

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

object DialogueWindowManager {
    fun showAlert(message: String, context: Context) {
        val toast = Toast.makeText(context,message,Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showExitDialogue(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.quit_alert_title))
        builder.setPositiveButton(context.getString(R.string.alert_yes_option)) {
            _, _ -> exitProcess(1)
        }
        builder.setNegativeButton(context.getString(R.string.alert_no_option)) {
            _, _ ->
        }
        builder.create()
        builder.show()
    }
}