package com.example.dronesexample.data.repository.request

import com.example.dronesexample.data.repository.request.model.ResponseModel
import com.example.dronesexample.data.repository.request.network.Either
import com.google.android.gms.common.api.Response

interface BaseRequestRepository {
    suspend fun makeRequest(block: (Either<ResponseModel>) -> Unit)
}

