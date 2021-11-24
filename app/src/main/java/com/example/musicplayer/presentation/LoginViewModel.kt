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
    val validator: UserDataValidator
) : ViewModel() {

    private val _loginState = MutableLiveData(true)
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
        setLoginState(true)
    }

    fun tryLoginUser(login: String, password: String) {
        viewModelScope.launch {
            val dataFromDB = userRepository.getUser(login)
            if (validator.checkLoginData(login, password, dataFromDB)) {
                setLoginState(true)
                _isLoginSuccessful.postValue(true)
                delay(1)
                _isLoginSuccessful.postValue(false)
            } else {
                _isLoginSuccessful.postValue(false)
            }
        }
    }

    fun setLoginState(newState: Boolean) {
        _loginState.postValue(newState)
        loginStateRepository.saveLoginState(newState)
    }
}