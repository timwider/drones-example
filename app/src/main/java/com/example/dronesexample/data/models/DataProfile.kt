package com.example.dronesexample.data.models

import com.example.dronesexample.models.Authorization
import com.example.dronesexample.models.Drone
import com.example.dronesexample.models.FlightPlans
import com.example.dronesexample.models.Profile

/**
 * 1) authorization should be ids
 * 2) profile should be ids or concatenated
 * 3) flight plans should be ids
 * 4) drones should be ids
 * 5) name is key because there's only 1 object
 */
class DataProfile(
    var username: String? = null,
    var password: String? = null,
    var authorization: Authorization? = null,
    var profile: Profile? = null,
    var flightPlans: Array<FlightPlans>? = null,
    var drones: Array<Drone>? = null
)