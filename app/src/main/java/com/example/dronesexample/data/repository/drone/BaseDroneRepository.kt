package com.example.dronesexample.data.repository.drone

import com.example.dronesexample.models.Drone

interface BaseDroneRepository {

    suspend fun getAllDrones(block: (List<Drone>) -> Unit)

    suspend fun getDroneById(id: Int, block: (Drone) -> Unit)

    suspend fun getDroneBySerial(serial: String, block: (Drone) -> Unit)

    suspend fun updateDrone(droneId: Int, drone: Drone, block: (Boolean) -> Unit)

    suspend fun deleteDrone(droneId: Int, block: (Boolean) -> Unit)

    suspend fun saveDrone(drone: Drone, block: (Boolean) -> Unit)
}