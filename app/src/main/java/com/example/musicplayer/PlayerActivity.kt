package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginManager = LoginManager(this)

        binding.exitButton.setOnClickListener {
            loginManager.unloginUser()
            val loginIntent = Intent(baseContext, RegisterActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }

    override fun onBackPressed() {}
}