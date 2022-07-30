package com.example.dronesexample.data.repository.authorization

import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.data.models.Authorization
import com.google.firebase.database.DatabaseReference

class AuthorizationRepository: BaseAuthorization {

    private val database: DatabaseReference by lazy { initDatabase() }

    override fun authorize(block: (Boolean) -> Unit) {
        database
            .child("authorization")
            .child("isAuthorized")
            .setValue(true)
            .addOnSuccessListener { block.invoke(true) }
    }

    override fun logOut(block: (Boolean) -> Unit) {
        database
            .child("authorization")
            .child("isAuthorized")
            .setValue(false)
            .addOnSuccessListener { block.invoke(false) }
    }

    override fun saveAuthCredentials(auth: Authorization, block: (Boolean) -> Unit){
        val operation = database.child("authorization").setValue(auth)
        operation.addOnSuccessListener { block.invoke(true) }
        operation.addOnFailureListener { block.invoke(false) }
    }

    override fun getAuthStatus(block: (Boolean) -> Unit) {
        database.child("authorization").child("isAuthorized").get().addOnSuccessListener {
            it.getValue(Boolean::class.java)?.let { isAuthorized -> block.invoke(isAuthorized) }
        }
    }

    override fun logIn(username: String, password: String, block: (Boolean, Boolean) -> Unit) {
        database.child("authorization").get().addOnSuccessListener { snap ->
            snap.getValue(Authorization::class.java)?.let {
                block.invoke(username == it.username, password == it.password)
            }
        }
    }

    private fun initDatabase() = FirebaseDatabaseReference.get()
}