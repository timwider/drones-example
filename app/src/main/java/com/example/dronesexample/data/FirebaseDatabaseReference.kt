package com.example.dronesexample.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

object FirebaseDatabaseReference {

    fun get(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }
}