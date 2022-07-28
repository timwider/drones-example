package com.example.dronesexample.data.repository.authorization

import com.example.dronesexample.models.Authorization

interface BaseAuthorization {

    fun authorize(username: String, password: String): Boolean

    // How do we get this? Token, Role, etc.
    fun saveAuthCredentials(auth: Authorization, block: (Boolean) -> Unit)
}