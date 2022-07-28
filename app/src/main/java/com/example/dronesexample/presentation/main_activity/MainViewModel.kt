package com.example.dronesexample.presentation.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel: ViewModel() {

    private val _isUserLoggedIn = MutableLiveData<String>()
    val isUserLoggedIn = _isUserLoggedIn as LiveData<String>

    fun onLoginDataReceived() { _isUserLoggedIn.value = "true" }

    fun onLogOut() { _isUserLoggedIn.value = "false" }

    fun setAuth(value: String) {
        _isUserLoggedIn.value = value
    }
}