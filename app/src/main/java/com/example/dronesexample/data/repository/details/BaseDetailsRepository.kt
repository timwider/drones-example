package com.example.dronesexample.data.repository.details

import com.example.dronesexample.data.models.Details
import com.google.firebase.database.DatabaseReference

interface BaseDetailsRepository {

    suspend fun getDetailsById(detailsId: String, block: (Details) -> Unit)

    suspend fun getAllDetails(block: (List<Details>) -> Unit)

    suspend fun saveDetails(details: Details, detailsId: String, block: (Boolean) -> Unit)

    suspend fun deleteDroneDetails(detailsId: String, bloc: (Boolean) -> Unit)

    fun initDatabase(): DatabaseReference

}