package com.example.dronesexample.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.data.repository.profile.ProfileRepository
import com.example.dronesexample.models.Drone
import com.example.dronesexample.models.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _drones = MutableLiveData<List<Drone>>()
    val drones = _drones as LiveData<List<Drone>>

    private val _profileData = MutableLiveData<Profile>()
    val profileData = _profileData as LiveData<Profile>

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult = _loginResult as LiveData<LoginResult>

    private val droneRepository = DroneRepository()
    private val profileRepository = ProfileRepository()

    fun resetLoginResult() { _loginResult.value = LoginResult.NOT_SET }

    fun getAllDrones() {
        viewModelScope.launch(Dispatchers.IO) { droneRepository.getAllDrones { _drones.postValue(it) } }
    }

    fun addDrone(drone: Drone) {
        val list = _drones.value?.toMutableList() ?: mutableListOf()
        list.add(drone)
        _drones.value = list
    }

    fun getProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getProfileData("") { profile -> _profileData.postValue(profile) }
        }
    }

    fun validateProfileInput(name: String, lastname: String, phone: String, birthday: String) {
        saveProfileData(name, lastname, phone, birthday, null)
    }

    private fun saveProfileData(name: String, lastname: String, phone: String, birthday: String, email: String?) {
         val profile = Profile(
             first_name = name,
             last_name = lastname, phone = phone,
             birthdate = birthday,
             email = email ?: _profileData.value?.email,
             email_confirmed = true,
             phone_confirmed = true,
             photo = "")

         viewModelScope.launch(Dispatchers.IO) { profileRepository.saveProfile(profile) }
    }

    fun logIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.logIn(username, password) { usernameValid, passwordValid ->
                _loginResult.value = when {
                    !usernameValid -> LoginResult.USERNAME_INVALID
                    usernameValid && !passwordValid -> LoginResult.PASSWORD_INVALID
                    usernameValid && passwordValid -> LoginResult.OK
                    else -> LoginResult.NOT_SET
                }
            }
        }
    }

    fun deleteDrone(drone: Drone) {
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.deleteDrone(drone.drone_id!!) {}
            droneRepository.getAllDrones { _drones.postValue(it) }
        }
    }
}

enum class LoginResult {
    OK,
    USERNAME_INVALID,
    PASSWORD_INVALID,
    NOT_SET
}

