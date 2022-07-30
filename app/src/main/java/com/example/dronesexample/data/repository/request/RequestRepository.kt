package com.example.dronesexample.data.repository.request

import com.example.dronesexample.data.repository.request.model.ResponseModel
import com.example.dronesexample.data.repository.request.network.Either
import com.example.dronesexample.data.repository.request.network.HttpUrlConnectionContainer

// first post
const val URL = "https://jsonplaceholder.typicode.com/posts/1"

class RequestRepository: BaseRequestRepository {

    private val container = HttpUrlConnectionContainer

    override suspend fun makeRequest(block: (Either<ResponseModel>) -> Unit) { container.makeGetRequest(URL, block) }
}