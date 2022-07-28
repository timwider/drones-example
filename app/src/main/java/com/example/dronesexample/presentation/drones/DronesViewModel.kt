package com.example.dronesexample.presentation.drones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.models.Drone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DronesViewModel: ViewModel() {

    private val _dataSavedSuccessfully = MutableLiveData<Drone>()
    val dataSavedSuccessfully = _dataSavedSuccessfully as LiveData<Drone>

    private val repository = DroneRepository()

    fun setDrone(drone: Drone) { _dataSavedSuccessfully.value = drone }

    fun validateData(name: String, serial: String, weight: String, block: (Drone) -> Unit) {
        sendData(name, serial, weight.toInt(), block)
    }

    private fun sendData(name: String, serial: String, weight: Int, block: (Drone) -> Unit) {
        val drone = Drone(
            drone_id = (0..1000).random(),
            model = name,
            serial = serial,
            weight = weight
        )
        viewModelScope.launch(Dispatchers.IO) { repository.saveDrone(drone) { result -> block.invoke(drone) } }
    }
}