package com.example.dronesexample.models

class Details(
    var type: String? = null,
    var takeoff: Point? = null,
    var landing: Point? = null,
    var center: Point? = null, // 0.0, 0.0
    var turnpoints: Array<Point>? = null,
    var points: Array<Point>? = null,
    var radius: Double = 0.0,
    var max_height: Int? = null,
    var min_height: Int? = null,
    var name: String? = null,
    var mission: String? = null,
    var alt_mission: String? = null,
    var favorites: Boolean? = null,
    var line_of_sight: Boolean? = null,
    var drones: Array<Int>? = null,
    var flight_days: Array<Boolean>? = null
)