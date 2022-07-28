package com.example.dronesexample.data.repository.flight_plans

import com.example.dronesexample.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.presentation.plans.mapper.FlightPlansPR
import com.google.firebase.database.DatabaseReference

// Can we update flight plans?
interface BaseFlightPlansRepository {

    suspend fun getFlightPlans(block: (List<FlightPlans>) -> Unit)

    suspend fun addFlightPlan(flightPlan: FlightPlans, block: (Boolean) -> Unit)

    suspend fun deleteFlightPlan(flightPlanId: Int, block: (Boolean) -> Unit)

    suspend fun getFlightPlanDrone(flightPlanId: Int, block: (Drone) -> Unit)

    fun initDatabase(): DatabaseReference
}