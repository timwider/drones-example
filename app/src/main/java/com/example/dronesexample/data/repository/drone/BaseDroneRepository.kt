package com.example.dronesexample.data.repository.drone

import com.example.dronesexample.data.models.Drone

interface BaseDroneRepository {

    suspend fun getAllDrones(block: (List<Drone>) -> Unit)

    suspend fun getDroneBySerial(serial: String, block: (Drone) -> Unit)

    suspend fun deleteDrone(droneId: String, block: (Boolean) -> Unit)

    suspend fun saveDrone(droneId: String, drone: Drone, block: (Boolean) -> Unit)

    suspend fun getDroneById(droneUUID: String, block: (Drone) -> Unit)
}