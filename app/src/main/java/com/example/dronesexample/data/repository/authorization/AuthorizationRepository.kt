package com.example.dronesexample.data.repository.authorization

import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.models.Authorization
import com.google.firebase.database.DatabaseReference

class AuthorizationRepository: BaseAuthorization {

    private val database: DatabaseReference by lazy { initDatabase() }

    override fun authorize(username: String, password: String): Boolean {
        return (username == "admin" && password == "admin")
    }

    override fun saveAuthCredentials(auth: Authorization, block: (Boolean) -> Unit){
        val operation = database.child("authorization").setValue(auth)

        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(true)
        }
    }

    private fun initDatabase() = FirebaseDatabaseReference.get()
}