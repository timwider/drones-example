package com.example.dronesexample.data.repository.drone

import android.util.Log
import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.models.Drone
import com.google.firebase.database.DatabaseReference

class DroneRepository: BaseDroneRepository {

    private val database: DatabaseReference by lazy { initDatabase() }

    private fun initDatabase(): DatabaseReference = FirebaseDatabaseReference.get()

    override suspend fun getAllDrones(block: (List<Drone>) -> Unit) {
        val drones = mutableListOf<Drone>()

        val operation = database.child("drones").get()

        operation.addOnSuccessListener { snap ->
            snap.children.forEach { droneSnapshot ->
                droneSnapshot.getValue(Drone::class.java)?.let {
                    drones.add(it)
                }
            }
            block.invoke(drones)
        }

        // if we fail loading from firebase, generate sample data
        // todo replace it with cache
        operation.addOnFailureListener {
            block.invoke(generateSampleDroneData())
        }
    }

    override suspend fun getDroneById(id: Int, block: (Drone) -> Unit) {

    }

    override suspend fun getDroneBySerial(serial: String, block: (Drone) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun updateDrone(droneId: Int, drone: Drone, block: (Boolean) -> Unit) {
        val operation = database.child("drones").child(droneId.toString()).setValue(drone)
        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun deleteDrone(droneId: Int, block: (Boolean) -> Unit) {
        val operation = database.child("drones").child(droneId.toString()).removeValue()
        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun saveDrone(drone: Drone, block: (Boolean) -> Unit) {
        val operation = database.child("drones").child("${drone.drone_id}").setValue(drone)
        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    fun generateSampleDroneData(): List<Drone> =
        listOf(
            Drone(drone_id = 0, serial = "1ABC32D32", model = "Drone Model X", weight = 3),
            Drone(drone_id = 1, serial = "BA1C34D132", model = "Drone Model Y", weight = 1),
            Drone(drone_id = 2, serial = "42AG32A32", model = "Drone Model Z", weight = 2),
            Drone(drone_id = 3, serial = "5ASCF2HD32", model = "Drone Model D", weight = 4),
            Drone(drone_id = 4, serial = "8AHHJ2D300", model = "Drone Model M", weight = 6),
        )
}