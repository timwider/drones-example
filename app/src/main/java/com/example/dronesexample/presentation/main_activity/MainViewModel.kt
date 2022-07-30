package com.example.dronesexample.presentation.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.models.Authorization
import com.example.dronesexample.data.repository.authorization.AuthorizationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel: ViewModel() {

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn = _isUserLoggedIn as LiveData<Boolean>

    private val authorizationRepository = AuthorizationRepository()

    fun onLoginDataReceived() {
        viewModelScope.launch(Dispatchers.IO) {
            authorizationRepository.authorize { _isUserLoggedIn.postValue(true) }
        }
    }

    fun onLogOut() {
        viewModelScope.launch(Dispatchers.IO) {
            authorizationRepository.logOut { _isUserLoggedIn.postValue(false) }
        }
    }

    fun getAuthData() {
        viewModelScope.launch(Dispatchers.IO) {
            authorizationRepository.getAuthStatus { _isUserLoggedIn.postValue(it) }
        }
    }
}