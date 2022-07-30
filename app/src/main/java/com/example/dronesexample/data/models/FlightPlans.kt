package com.example.dronesexample.data.models

class FlightPlans(
    var uuid: String? = null,
    var period_start: String? = null,
    var period_end: String? = null,
    var details_id: String? = null,
    var permission: String? = null,
    var notified: String? = null,
    var droneUUID: String? = null,
)

class FlightPlansRV(
    var uuid: String,
    var periodStart: String? = null,
    var periodEnd: String? = null,
    var isFavorite: Boolean? = null,
    var droneName: String? = null,
    var permission: String? = null,
    var detailsId: String? = null
)