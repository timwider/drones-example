package com.example.dronesexample.data.models

import android.os.Parcelable
import com.example.dronesexample.models.DroneDetails
import java.io.Serializable

// key = drone_id, keys are stored anywhere we need drones
// fields from DroneDetails = name, reg_no
class Drone(
    var uuid: String? = null,
    var name: String? = null,
    var reg_no: String? = null,
    var serial: String? = null,
    var weight: Int? = null,
    var model: String? = null,
    var photo: String? = null,
    var doc: String? = null,
): Serializable