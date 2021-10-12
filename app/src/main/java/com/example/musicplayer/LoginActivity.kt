package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginManager = LoginManager(this)

        val recyclerView = binding.inputFieldList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EditTextListAdapter(
            arrayOf(
                getString(R.string.login_text_field),
                getString(R.string.password_text_field)
            ), arrayOf(1, 1 or 128)
        )

        val rvAdapter = recyclerView.adapter as EditTextListAdapter

        binding.loginButton.setOnClickListener {
            if (loginManager.checkLoginData(
                    rvAdapter.getTypedTexts()[0],
                    rvAdapter.getTypedTexts()[1]
                )
            ) {
                loginManager.loginUser()
                val playerIntent = Intent(baseContext, PlayerActivity::class.java)
                startActivity(playerIntent)
                finish()
            } else DialogueWindowManager.showAlert("Неверный логин или пароль", this)
        }

        binding.newAccountButton.setOnClickListener {
            val signUpIntent = Intent(baseContext, RegisterActivity::class.java)
            startActivity(signUpIntent)
            finish()
        }
    }
}