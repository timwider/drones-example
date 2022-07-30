package com.example.dronesexample

import android.app.Application
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class Application : Application() {

    override fun onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().reference.keepSynced(true)

        super.onCreate()
    }
}