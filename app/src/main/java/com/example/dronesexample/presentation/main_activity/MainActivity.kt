package com.example.dronesexample.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.dronesexample.R
import com.example.dronesexample.presentation.home.HomeFragment
import com.example.dronesexample.presentation.navigation.Destination
import com.example.dronesexample.presentation.navigation.NavigationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getAuthData()

        NavigationManager(supportFragmentManager).navigate(Destination.HOME)
    }
}