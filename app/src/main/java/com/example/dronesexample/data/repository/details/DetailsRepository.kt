package com.example.dronesexample.data.repository.details

import android.util.Log
import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.data.models.Details
import com.google.firebase.database.DatabaseReference

class DetailsRepository: BaseDetailsRepository {

    private val database: DatabaseReference by lazy { initDatabase() }

    override suspend fun getDetailsById(detailsId: String, block: (Details) -> Unit) {

        val operation = database.child("details").child(detailsId).get()

        operation.addOnSuccessListener { snap -> snap.getValue(Details::class.java)?.let { block.invoke(it) } }
    }

    override suspend fun getAllDetails(block: (List<Details>) -> Unit) {

        val detailsList = mutableListOf<Details>()

        database.child("details").get().addOnSuccessListener {
            it.children.forEach { snap ->
                snap.getValue(Details::class.java)?.let { details -> detailsList.add(details) }
            }
            block.invoke(detailsList)
        }
    }

    override suspend fun saveDetails(
        details: Details,
        detailsId: String,
        block: (Boolean) -> Unit
    ) {
        val operation = database.child("details").child(detailsId).setValue(details)

        operation.addOnSuccessListener { block.invoke(true) }
        operation.addOnFailureListener { block.invoke(false) }
    }

    override suspend fun deleteDroneDetails(detailsId: String, bloc: (Boolean) -> Unit) {
        database.child("details").child(detailsId).removeValue()
    }

    override fun initDatabase(): DatabaseReference = FirebaseDatabaseReference.get()
}