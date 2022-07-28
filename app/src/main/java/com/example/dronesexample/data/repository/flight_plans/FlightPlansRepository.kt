package com.example.dronesexample.data.repository.flight_plans

import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.presentation.plans.mapper.FlightPlansMapper
import com.example.dronesexample.presentation.plans.mapper.FlightPlansPR
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

    override suspend fun addFlightPlan(flightPlan: FlightPlans, block: (Boolean) -> Unit) {
        val operation = database.child("flight_plans").child(flightPlan.plan_id.toString()).setValue(flightPlan)

        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun deleteFlightPlan(flightPlanId: Int, block: (Boolean) -> Unit) {
        val operation = database.child("flight_plans").child(flightPlanId.toString()).removeValue()

        operation.addOnSuccessListener {
            block.invoke(true)
        }

        operation.addOnFailureListener {
            block.invoke(false)
        }
    }

    override suspend fun getFlightPlanDrone(flightPlanId: Int, block: (Drone) -> Unit) {
        database.child("flight_plans").child(flightPlanId.toString()).child("drone").get().addOnSuccessListener {
            val value = it.getValue(Int::class.java)
            database.child("drones").orderByChild("drone_id").equalTo(it.toString())
        }
    }

    override fun initDatabase(): DatabaseReference {
        return FirebaseDatabaseReference.get()
    }

}