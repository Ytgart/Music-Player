package com.example.musicplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LoginStateRepository
import com.example.musicplayer.data.PlayerUserRepository
import com.example.musicplayer.data.database.PlayerUser
import com.example.musicplayer.helpers.UserDataValidator
import kotlinx.coroutines.launch

enum class LoginResponse {
    WAITING_FOR_DATA,
    SUCCESSFUL,
    NOT_SUCCESSFUL,
}

class LoginViewModel(
    private val userRepository: PlayerUserRepository,
    val loginStateRepository: LoginStateRepository,
    val validator: UserDataValidator = UserDataValidator()
) : ViewModel() {

    private val _isLoginSuccessful = MutableLiveData(LoginResponse.WAITING_FOR_DATA)
    val isLoginSuccessful: LiveData<LoginResponse>
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
                _isLoginSuccessful.postValue(LoginResponse.SUCCESSFUL)
            } else {
                _isLoginSuccessful.postValue(LoginResponse.NOT_SUCCESSFUL)
            }
        }
    }

    fun resetLoginResponse() = _isLoginSuccessful.postValue(LoginResponse.WAITING_FOR_DATA)
}