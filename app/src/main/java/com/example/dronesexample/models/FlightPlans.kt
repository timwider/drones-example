package com.example.dronesexample.models

// change perm and notif
// result showcase
class FlightPlans(
    var plan_id: String? = null,
    var period_start: String? = null,
    var period_end: String? = null,
    var details: Details? = null,
    var permission: String? = null,
    var notified: String? = null,
    var drone: Drone? = null
)