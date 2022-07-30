package com.example.dronesexample.data.repository.profile

import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.data.models.Authorization
import com.example.dronesexample.data.models.Profile
import com.google.firebase.database.DatabaseReference

class ProfileRepository: BaseProfileRepository {

    private val database =  FirebaseDatabaseReference.get()

    override suspend fun getProfileData(token: String, block: (Profile) -> Unit) {
        val profile = Profile()

        database.child("profile").get().addOnSuccessListener { snap ->
            snap.getValue(Profile::class.java)?.let {
                profile.email = it.email
                profile.phone = it.phone
                profile.first_name = it.first_name
                profile.last_name = it.last_name
                profile.birthdate = it.birthdate
                profile.email_confirmed = it.email_confirmed
                profile.phone_confirmed = it.phone_confirmed
                profile.photo = it.photo
                profile.username = it.username
                profile.password = it.password
            }
            block.invoke(profile)
        }
    }

    override suspend fun saveProfile(profile: Profile): Boolean {
        database.child("profile").setValue(profile)
        return true
    }
}