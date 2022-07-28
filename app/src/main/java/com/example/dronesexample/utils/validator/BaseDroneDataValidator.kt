package com.example.dronesexample.utils.validator

interface BaseDroneDataValidator {

    fun validateSerialNumber(serialNumber: String): Boolean

    fun validateDroneName(droneName: String): Boolean

    fun validateDroneWeight(weight: String): Boolean
}