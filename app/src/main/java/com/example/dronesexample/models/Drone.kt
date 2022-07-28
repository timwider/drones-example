package com.example.dronesexample.models

import java.io.Serializable


class Drone(
    var drone_id: Int? = null,
    var serial: String? = null,
    var weight: Int? = null,
    var model: String? = null,
    var photo: String? = null,
    var doc: String? = null,
    var details: DroneDetails? = null
): Serializable