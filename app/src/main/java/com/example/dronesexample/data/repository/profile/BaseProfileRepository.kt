package com.example.dronesexample.data.repository.profile

import com.example.dronesexample.models.Profile

// Do we get profile with token?
interface BaseProfileRepository {

    suspend fun getProfileData(token: String, block: (Profile) -> Unit)

    suspend fun saveProfile(profile: Profile): Boolean

    suspend fun logIn(username: String, password: String, block: (Boolean, Boolean) -> Unit)

}