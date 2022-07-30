package com.example.dronesexample.data.repository.profile

import com.example.dronesexample.data.models.Profile

// Do we get profile with token?
interface BaseProfileRepository {

    suspend fun getProfileData(token: String, block: (Profile) -> Unit)

    suspend fun saveProfile(profile: Profile): Boolean
}