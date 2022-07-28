package com.example.dronesexample.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.dronesexample.R
import com.example.dronesexample.data.local.PreferencesManager
import com.example.dronesexample.presentation.home.HomeFragment


const val SP_USER_AUTH_KEY = "isUserAuthenticated"

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pm = PreferencesManager(applicationContext)
        val isAuthenticated = pm.getString(SP_USER_AUTH_KEY)!!
        viewModel.setAuth(isAuthenticated)

        viewModel.isUserLoggedIn.observe(this) {
            if (it == "true") pm.putString(SP_USER_AUTH_KEY, it)
        }

        supportFragmentManager.commit { replace<HomeFragment>(R.id.main_fragment_container) }
    }
}