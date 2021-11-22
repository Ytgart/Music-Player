package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LoginStateRepository
import com.example.musicplayer.data.PlayerUserRepository
import com.example.musicplayer.data.database.PlayerUser
import com.example.musicplayer.helpers.UserDataValidator
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginStateRepository: LoginStateRepository,
    private val userRepository: PlayerUserRepository,
    private val validator: UserDataValidator
) : ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _isLoginSuccessful = MutableLiveData(false)
    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    private val _isRegisterButtonEnabled = MutableLiveData(false)
    val isRegisterButtonEnabled: LiveData<Boolean>
        get() = _isRegisterButtonEnabled

    init {
        _loginState.postValue(loginStateRepository.getLoginState())
    }

    fun setRegisterButtonEnabled(newState: Boolean) = _isRegisterButtonEnabled.postValue(newState)

    fun registerUser(user: PlayerUser) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
        loginStateRepository.saveLoginState(true)
    }

    fun tryLoginUser(login: String, password: String) {
        viewModelScope.launch {
            val dataFromDB = userRepository.getUser(login)
            if (validator.checkLoginData(login, password, dataFromDB)) {
                loginStateRepository.saveLoginState(true)
                _isLoginSuccessful.postValue(true)
                delay(1)
                _isLoginSuccessful.postValue(false)
            } else {
                _isLoginSuccessful.postValue(false)
            }
        }
    }

    fun isCorrectInput(list: Array<TextInputEditText>): Boolean {
        validator.validateFields(list)
        return validator.hasNoErrors(list)
    }

    fun setValidationListeners(list: Array<TextInputEditText>) {
        validator.setValidationListeners(list)
    }
}