package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginManager: LoginManager
    private val userDataValidator = UserDataValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginManager = LoginManager(this)

        if (loginManager.isLogged()) {
            val playerIntent = Intent(baseContext, PlayerActivity::class.java)
            startActivity(playerIntent)
            finish()
        }

        val recyclerView = binding.inputFieldList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EditTextListAdapter(
            arrayOf(
                getString(R.string.login_text_field),
                getString(R.string.email_text_field),
                getString(R.string.password_text_field),
                getString(R.string.repeat_password_text_field)
            ), arrayOf(1,1 or 32, 1 or 128,1 or 128)
        )

        val rvAdapter = recyclerView.adapter as EditTextListAdapter

        binding.loginButton.setOnClickListener {
            val typedTexts = rvAdapter.getTypedTexts()
            val newUserData =
                LoginManager.UserData(typedTexts[0], typedTexts[1], typedTexts[2])

            when (userDataValidator.validate(newUserData, typedTexts[3])) {
                0 -> {
                    if (binding.registerCheckBox.isChecked) {
                        loginManager.registerUser(newUserData)
                        val playerIntent = Intent(baseContext, PlayerActivity::class.java)
                        startActivity(playerIntent)
                        finish()
                    } else DialogueWindowManager.showAlert(
                        "Вы не приняли условия пользования",
                        this
                    )
                }
                1 -> DialogueWindowManager.showAlert("Пароли не совпадают", this)
                2 -> DialogueWindowManager.showAlert("Некорректный E-Mail", this)
                3 -> DialogueWindowManager.showAlert("Некорректный логин", this)
            }
        }

        binding.newAccountButton.setOnClickListener {
            val loginIntent = Intent(baseContext, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }
}