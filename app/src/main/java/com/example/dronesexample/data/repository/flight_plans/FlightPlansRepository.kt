package com.example.dronesexample.data.repository.flight_plans

import android.util.Log
import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.data.models.Details
import com.example.dronesexample.data.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.google.firebase.database.DatabaseReference

class FlightPlansRepository: BaseFlightPlansRepository {

    private val database: DatabaseReference by lazy { initDatabase() }
    private val droneRepository = DroneRepository()

    override suspend fun getFlightPlans(block: (List<FlightPlans>) -> Unit) {

        val plans = mutableListOf<FlightPlans>()

        database.child("flight_plans").get().addOnSuccessListener {
            it.children.forEach { snap ->
                snap.getValue(FlightPlans::class.java)?.let { plan ->
                    plans.add(plan)
                }
            }
            block.invoke(plans)
        }
    }

    override suspend fun addFlightPlan(
        uuid: String,
        flightPlan: FlightPlans,
        block: (Boolean) -> Unit
    ) {
        val operation = database.child("flight_plans").child(uuid).setValue(flightPlan)

        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun deleteFlightPlan(uuid: String, block: (Boolean) -> Unit) {
        val operation = database.child("flight_plans").child(uuid).removeValue()

        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun getFlightPlanDrone(droneUUID: String, block: (Drone) -> Unit) {
        droneRepository.getDroneById(droneUUID) {
            block.invoke(it)
        }
    }

    override fun initDatabase(): DatabaseReference {
        return FirebaseDatabaseReference.get()
    }

    override suspend fun deleteDronePlans(droneUUID: String) {
        val operation = database.child("flight_plans").orderByChild("droneUUID").equalTo(droneUUID).get()

        operation.addOnSuccessListener { snap ->
            snap.children.forEach { it.ref.removeValue() }
        }
    }

    override suspend fun getFlightDetailsId(droneUUID: String, block: (String) -> Unit) {
        val operation =
            database.child("flight_plans").orderByChild("droneUUID").equalTo(droneUUID).get()
        operation.addOnSuccessListener { snap ->
            snap.children.forEach { flightPlan ->
                flightPlan.getValue(FlightPlans::class.java)?.let {
                    block.invoke(it.details_id!!)
                }
            }
        }
    }

    override suspend fun toggleFavorite(newStatus: Boolean, detailsId: String, block: (Boolean) -> Unit) {
        database.child("details").child(detailsId).child("favorites").setValue(newStatus)
    }
}