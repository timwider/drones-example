package com.example.dronesexample.presentation.edit_plans

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.models.Details
import com.example.dronesexample.data.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.data.models.Point
import com.example.dronesexample.data.repository.details.DetailsRepository
import com.example.dronesexample.data.repository.drone.DroneRepository
import com.example.dronesexample.data.repository.flight_plans.FlightPlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EditPlansViewModel: ViewModel() {

    // whether new data has been loaded to database or not
    private val _validationResult = MutableLiveData<Boolean>()
    val validationResult = _validationResult as LiveData<Boolean>

    val selectedDroneUUID = MutableLiveData<String>()

    private val detailsRepository = DetailsRepository()
    private val flightPlansRepository = FlightPlansRepository()

    fun onDroneSelected(uuid: String) { selectedDroneUUID.value = uuid }

    fun validateData(name: String, from: String, to: String, selectedDroneUUID: String) {
        // after data is validated, we get drone and then - FlightPlan

        val randomPermission = listOf("accepted", "declined").random()

        val details = generateDetailsData(name, selectedDroneUUID)

        val flightPlan = FlightPlans(
            period_start = from,
            period_end = to,
            details_id = details.uuid,
            permission = randomPermission,
            notified = "notified",
            uuid = generateRandomId(),
            droneUUID = selectedDroneUUID
        )

        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.saveDetails(details, details.uuid!!) {}
            flightPlansRepository.addFlightPlan(flightPlan.uuid!!, flightPlan) {}
            _validationResult.postValue(true)
        }

    }

    private fun generateDetailsData(name: String, droneId: String): Details {
        return Details(
            uuid = generateRandomId(),
            type = "type",
            takeoff = Point(0.0, 0.0),
            landing = Point(0.0, 0.0),
            turnpoints = null,
            points = null,
            radius = 0.0,
            max_height = 100,
            min_height = 200,
            name = name,
            mission = "mission",
            alt_mission = "alt_mission",
            favorites = false,
            line_of_sight = false,
            drones = listOf(droneId),
            flight_days = listOf(false)
        )
    }

    private fun generateRandomId(): String {

        val letters = "qwertyuiopasdfghjklzxcvbnm"
        var id = ""

        for (i in (0..15)) {
            // letters size = 25
            val letter = letters.get((0..24).random())
            val num = Random().nextInt(10).toString()
            id += num + letter
        }
        return id
    }
}