package com.example.dronesexample.data.repository.drone

import android.util.Log
import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.data.models.Drone
import com.google.firebase.database.*

class DroneRepository: BaseDroneRepository {

    private val database: DatabaseReference by lazy { initDatabase() }

    private fun initDatabase(): DatabaseReference = FirebaseDatabaseReference.get()

    override suspend fun getAllDrones(block: (List<Drone>) -> Unit) {
        database.child("drones").keepSynced(true)
        val drones = mutableListOf<Drone>()

        val operation = database.child("drones").get()

        operation.addOnSuccessListener { snap ->
            snap.children.forEach { droneSnapshot ->
                droneSnapshot.getValue(Drone::class.java)?.let { drones.add(it) }
            }
            block.invoke(drones)
        }
    }

    override suspend fun getDroneBySerial(serial: String, block: (Drone) -> Unit) {
        val operation = database.child("drones").orderByChild("serial").equalTo(serial).get()
        operation.addOnSuccessListener { snapshot ->
            val drone = Drone()
            snapshot.children.forEach { snap ->
                snap.getValue(Drone::class.java)?.let { foundDrone ->
                    drone.uuid = foundDrone.uuid
                    drone.name = foundDrone.name
                    drone.reg_no = foundDrone.reg_no
                    drone.serial = foundDrone.serial
                    drone.weight = foundDrone.weight
                    drone.model = foundDrone.model
                    drone.doc = foundDrone.doc
                    drone.photo = foundDrone.photo
                }
            }
        }
    }

    override suspend fun deleteDrone(uuid: String, block: (Boolean) -> Unit) {
        database.child("drones").child(uuid).removeValue()
    }

    override suspend fun saveDrone(droneId: String, drone: Drone, block: (Boolean) -> Unit) {
        val operation = database.child("drones").child(droneId).setValue(drone)
        operation.addOnSuccessListener { block.invoke(true) }
        operation.addOnFailureListener { block.invoke(false) }
    }

    override suspend fun getDroneById(droneUUID: String, block: (Drone) -> Unit) {
        database.child("drones").child(droneUUID).get().addOnSuccessListener { snap ->
            snap.getValue(Drone::class.java)?.let { drone -> block.invoke(drone) }
        }
    }

    private fun generateSampleDroneData(): List<Drone> =
        listOf(
            Drone(serial = "1ABC32D32", model = "Drone Model X", weight = 3, uuid = "032tpwfweo"),
            Drone(serial = "BA1C34D132", model = "Drone Model Y", weight = 1, uuid = "woeff320"),
            Drone(serial = "42AG32A32", model = "Drone Model Z", weight = 2, uuid = "kger402"),
            Drone(serial = "5ASCF2HD32", model = "Drone Model D", weight = 4, uuid = "owefof30"),
            Drone(serial = "8AHHJ2D300", model = "Drone Model M", weight = 6, uuid = "tiwefk43"),
        )
}

