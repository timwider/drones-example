package com.example.dronesexample.data.repository.flight_plans

import android.text.BoringLayout
import com.example.dronesexample.data.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.presentation.plans.mapper.FlightPlansPR
import com.google.firebase.database.DatabaseReference

// Can we update flight plans?
interface BaseFlightPlansRepository {

    suspend fun getFlightPlans(block: (List<FlightPlans>) -> Unit)

    suspend fun addFlightPlan(uuid: String, flightPlan: FlightPlans, block: (Boolean) -> Unit)

    suspend fun deleteFlightPlan(uuid: String, block: (Boolean) -> Unit)

    suspend fun getFlightPlanDrone(droneUUID: String, block: (Drone) -> Unit)

    fun initDatabase(): DatabaseReference

    suspend fun deleteDronePlans(droneUUID: String)

    suspend fun getFlightDetailsId(droneUUID: String, block: (String) -> Unit)

    suspend fun toggleFavorite(newStatus: Boolean, detailsId: String, block: (Boolean) -> Unit)
}