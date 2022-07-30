package com.example.dronesexample.presentation.drones

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.data.models.Drone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class DronesViewModel: ViewModel() {

    private val _dataSavedSuccessfully = MutableLiveData<Drone>()
    val dataSavedSuccessfully = _dataSavedSuccessfully as LiveData<Drone>

    private val repository = DroneRepository()

    fun setDrone(drone: Drone) { _dataSavedSuccessfully.value = drone }

    fun validateData(name: String, serial: String, weight: String, block: (Drone) -> Unit) {
        sendData(name, serial, weight.toInt(), block)
    }

    private fun sendData(name: String, serial: String, weight: Int, block: (Drone) -> Unit) {
        val uuid = generateRandomDroneId()
        val drone = Drone(
            model = name,
            serial = serial,
            weight = weight,
            uuid = uuid
        )

        viewModelScope.launch(Dispatchers.IO) { repository.saveDrone(uuid, drone) { result -> block.invoke(drone) } }
    }

    private fun generateRandomDroneId(): String {
        val random = Random
        val letters = "qwertyuiopasdfghklzxcvbnm"
        var id = ""
        // if we want ID to be 16 characters
        for (i in (0..15)) {
            val letter = letters.random()
            val num = random.nextInt(10)
            id += "$letter$num"
        }
        return id
    }
}