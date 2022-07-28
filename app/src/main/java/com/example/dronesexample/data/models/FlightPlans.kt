package com.example.dronesexample.data.models

import com.example.dronesexample.models.Details
import com.example.dronesexample.models.Drone

// drone should be id
// key = plan_id, keys are stored anywhere we need access to flight plans

class FlightPlans(
    var plan_id: String? = null,
    var period_start: String? = null,
    var period_end: String? = null,
    // plans id
    var details: MutableList<Int>? = null,
    var permission: String? = null,
    var notified: String? = null,
    // drone_id here
    var drone: Int? = null
)