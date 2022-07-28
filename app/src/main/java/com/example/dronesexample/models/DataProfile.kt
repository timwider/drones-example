package com.example.dronesexample.models

class DataProfile(
    var username: String? = null,
    var password: String? = null,
    var authorization: Authorization? = null,
    var profile: Profile? = null,
    var flightPlans: Array<FlightPlans>? = null,
    var drones: Array<Drone>? = null
)