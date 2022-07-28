package com.example.dronesexample.utils.validator

import androidx.core.text.isDigitsOnly
import java.lang.NumberFormatException

const val MIN_SERIAL_LENGTH = 5
const val MAX_SERIAL_LENGTH = 15

const val MIN_DRONE_WEIGHT = 1.0
const val MAX_DRONE_WEIGHT = 20.0

class DroneDataValidator: BaseDroneDataValidator {

    override fun validateSerialNumber(serialNumber: String): Boolean {

        // proper length
        if (serialNumber.length !in (MIN_SERIAL_LENGTH..MAX_SERIAL_LENGTH)) return false

        serialNumber.forEach {
            if (it.isDigit()) {
                return@forEach
            }
        }

        // has chars
        if (serialNumber.isDigitsOnly()) return false

        return true
    }

    override fun validateDroneName(droneName: String): Boolean {
        return droneName.isNotBlank()
    }

    override fun validateDroneWeight(weight: String): Boolean {

        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return false
        }

        if (weight.toDouble() in (MIN_DRONE_WEIGHT..MAX_DRONE_WEIGHT)) return false
        return true
    }

}

