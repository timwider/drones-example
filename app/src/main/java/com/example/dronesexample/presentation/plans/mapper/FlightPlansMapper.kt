package com.example.dronesexample.presentation.plans.mapper

import com.example.dronesexample.models.Details
import com.example.dronesexample.models.Drone
import com.example.dronesexample.models.FlightPlans

class FlightPlansMapper {

    fun fromDataToPresentation(model: FlightPlans) {

    }

    fun fromPresentationToData() {

    }
}

//
class FlightPlansPR(
    var plan_id: String? = null,
    var period_start: String? = null,
    var period_end: String? = null,
    var details: Details? = null,
    var permission: String? = null,
    var notified: String? = null,
    var drone: Drone? = null,
    var isFavorite: Boolean? = null
)