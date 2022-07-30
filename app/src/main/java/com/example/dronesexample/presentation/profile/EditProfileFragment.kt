package com.example.dronesexample.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.*
import com.example.dronesexample.R
import com.example.dronesexample.databinding.EditProfileFragmentBinding
import com.example.dronesexample.presentation.home.HomeFragment
import com.example.dronesexample.presentation.home.HomeViewModel
import com.example.dronesexample.presentation.home.LoginResult
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.navigation.Destination
import com.example.dronesexample.presentation.navigation.NavigationManager
import com.google.android.material.snackbar.Snackbar

class EditProfileFragment: Fragment(R.layout.edit_profile_fragment) {

    private val binding: EditProfileFragmentBinding by lazy { initBinding() }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) showLayout() else hideLayout()
        }

        val profileData = homeViewModel.profileData.value

        // todo if not loaded, make network request
        binding.etProfileName.setText(profileData?.first_name)
        binding.etProfileLastname.setText(profileData?.last_name)
        binding.etProfilePhone.setText(profileData?.phone)
        binding.etProfileBirthday.setText(profileData?.birthdate)

        // а если юзеров много?
        homeViewModel.loginResult.observe(viewLifecycleOwner) { status ->
            var message = ""
            message = when(status) {
                LoginResult.USERNAME_INVALID -> "Неверное имя пользователя"
                LoginResult.PASSWORD_INVALID -> "Неверный пароль"
                LoginResult.OK -> "Успешно"
                LoginResult.NOT_SET -> ""
            }

            if (message.isNotEmpty()) Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()

            // if data is ok and user is NOT logged in right now
            if (status == LoginResult.OK && mainViewModel.isUserLoggedIn.value == false) {
                mainViewModel.onLoginDataReceived()
                homeViewModel.resetLoginResult()
                navigateHome()
            }
        }

        binding.btnConfirmEdit.setOnClickListener {
            homeViewModel.validateProfileInput(
                name = binding.etProfileName.text.toString(),
                lastname = binding.etProfileLastname.text.toString(),
                phone = binding.etProfilePhone.text.toString(),
                birthday = binding.etProfileBirthday.text.toString(),
            )
            homeViewModel.resetLoginResult()
            navigateHome()
        }

        binding.btnLogIn.setOnClickListener {
            homeViewModel.logIn(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

        binding.btnLogOut.setOnClickListener {
            homeViewModel.resetLoginResult()
            mainViewModel.onLogOut()
        }

        binding.btnCancelEdit.setOnClickListener { navigateHome() }
        binding.btnCancel.setOnClickListener {  navigateHome() }
    }

    private fun hideLayout() {

        binding.root.children.forEach { it.visibility = View.GONE }

        binding.btnLogIn.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE

        binding.etUsernameLayout.visibility = View.VISIBLE
        binding.etPasswordLayout.visibility = View.VISIBLE
        binding.tvEditProfile.visibility = View.VISIBLE
        binding.tvEditProfile.text = "Вход в аккаунт"
    }

    private fun showLayout() {
        binding.root.children.forEach { it.visibility = View.VISIBLE }

        binding.btnLogIn.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE

        binding.etUsernameLayout.visibility = View.GONE
        binding.etPasswordLayout.visibility = View.GONE
    }

    private fun navigateHome() {
        NavigationManager(parentFragmentManager).navigate(Destination.HOME)
    }

    private fun initBinding() = EditProfileFragmentBinding.bind(requireView())
}