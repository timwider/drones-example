package com.example.dronesexample.data.repository.authorization

import com.example.dronesexample.data.models.Authorization

interface BaseAuthorization {

    fun authorize(block: (Boolean) -> Unit)

    fun logOut(block: (Boolean) -> Unit)

    fun saveAuthCredentials(auth: Authorization, block: (Boolean) -> Unit)

    fun getAuthStatus(block: (Boolean) -> Unit)

    fun logIn(username: String, password: String, block: (Boolean, Boolean) -> Unit)
}
