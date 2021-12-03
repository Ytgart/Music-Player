package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LoginStateRepository
import com.example.musicplayer.data.PlayerUserRepository
import com.example.musicplayer.data.database.PlayerUser
import com.example.musicplayer.helpers.UserDataValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: PlayerUserRepository,
    val loginStateRepository: LoginStateRepository,
    val validator: UserDataValidator = UserDataValidator()
) : ViewModel() {

    private val _isLoginSuccessful = MutableLiveData(false)
    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    private val _isRegisterButtonEnabled = MutableLiveData(false)
    val isRegisterButtonEnabled: LiveData<Boolean>
        get() = _isRegisterButtonEnabled

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
}