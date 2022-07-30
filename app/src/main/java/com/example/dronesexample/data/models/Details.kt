package com.example.dronesexample.data.models

import com.example.dronesexample.data.models.Point

// probably identical to app model
// key is random id. keys should be stored as list anywhere we need access to details
class Details(
    var uuid: String? = null,
    var type: String? = null,
    var takeoff: Point? = null,
    var landing: Point? = null,
    var center: Point? = null, // 0.0, 0.0
    var turnpoints: List<Point>? = null,
    var points: List<Point>? = null,
    var radius: Double = 0.0,
    var max_height: Int? = null,
    var min_height: Int? = null,
    var name: String? = null,
    var mission: String? = null,
    var alt_mission: String? = null,
    var favorites: Boolean? = null,
    var line_of_sight: Boolean? = null,
    var drones: List<String>? = null,
    var flight_days: List<Boolean>? = null
)