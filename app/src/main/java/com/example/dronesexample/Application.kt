package com.example.dronesexample

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class Application : Application() {

    override fun onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        super.onCreate()
    }
}