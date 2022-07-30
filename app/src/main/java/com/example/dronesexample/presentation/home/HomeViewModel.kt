package com.example.dronesexample.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.data.repository.profile.ProfileRepository
import com.example.dronesexample.data.models.Drone
import com.example.dronesexample.data.models.Profile
import com.example.dronesexample.data.repository.authorization.AuthorizationRepository
import com.example.dronesexample.data.repository.details.DetailsRepository
import com.example.dronesexample.data.repository.flight_plans.FlightPlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _drones = MutableLiveData<MutableList<Drone>>()
    val drones = _drones as LiveData<List<Drone>>

    private val _profileData = MutableLiveData<Profile>()
    val profileData = _profileData as LiveData<Profile>

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult = _loginResult as LiveData<LoginResult>

    private val droneRepository = DroneRepository()
    private val profileRepository = ProfileRepository()
    private val plansRepository = FlightPlansRepository()
    private val detailsRepository = DetailsRepository()
    private val authorizationRepository = AuthorizationRepository()

    fun extractDronesNames(): List<String> {
        val names = mutableListOf<String>()

        _drones.value?.forEach { names.add(it.model.toString()) }

        return names.toList()
    }

    fun resetLoginResult() { _loginResult.value = LoginResult.NOT_SET }

    fun getAllDrones() {
        viewModelScope.launch(Dispatchers.IO) { droneRepository.getAllDrones { _drones.postValue(it.toMutableList()) } }
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
            authorizationRepository.logIn(username, password) { usernameValid, passwordValid ->
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

        val currentDrones = _drones.value!!
        currentDrones.remove(drone)

        viewModelScope.launch(Dispatchers.IO) {
            plansRepository.getFlightDetailsId(drone.uuid.toString()) { detailsId ->
                viewModelScope.launch(Dispatchers.IO) {
                    detailsRepository.deleteDroneDetails(detailsId) {}
                }

                viewModelScope.launch(Dispatchers.IO) {
                    deletePlansAndDrone(drone.uuid.toString())
                }
            }
        }
    }

    private suspend fun deletePlansAndDrone(droneId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            plansRepository.deleteDronePlans(droneId)
            viewModelScope.launch(Dispatchers.IO) { droneRepository.deleteDrone(droneId) {} }
        }
    }
}

enum class LoginResult {
    OK,
    USERNAME_INVALID,
    PASSWORD_INVALID,
    NOT_SET
}

