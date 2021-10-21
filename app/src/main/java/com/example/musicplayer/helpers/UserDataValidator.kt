package com.example.musicplayer.helpers

import android.text.TextUtils
import com.example.musicplayer.afterTextChanged
import com.example.musicplayer.data.database.PlayerUser
import com.example.musicplayer.validate
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UserDataValidator() {
    fun validateFields(inputFields: Array<TextInputEditText>) {
        inputFields[0].validate("Поле не должно быть пустым") { s -> s.isNotEmpty() }
        inputFields[1].validate("Некорректный E-Mail") { s -> validateEmail(s) }
        inputFields[2].validate("Поле не должно быть пустым") { s -> s.isNotEmpty() }
        inputFields[3].validate("Пароли не совпадают") {
            validatePassword(
                inputFields[2].text.toString(),
                inputFields[3].text.toString()
            )
        }
    }

    fun setValidationListeners(inputFields: Array<TextInputEditText>) {
        for (i in 0..3) {
            inputFields[i].afterTextChanged {
                (inputFields[i].parent.parent as TextInputLayout).error = null
            }
        }
    }

    fun hasNoErrors(inputFields: Array<TextInputEditText>): Boolean {
        var result = true
        for (i in 0..3) {
            result = result && (inputFields[i].parent.parent as TextInputLayout).error == null
        }
        return result
    }

    private fun validateEmail(data: String): Boolean {
        return (!TextUtils.isEmpty(data)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches())
    }

    private fun validatePassword(password: String, repeatedPassword: String): Boolean {
        return (password == repeatedPassword && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
            repeatedPassword
        ))
    }

    fun checkLoginData(login: String, password: String, userData: PlayerUser?): Boolean {
        return if (userData != null) (login == userData.login && password == userData.password)
        else false
    }
}