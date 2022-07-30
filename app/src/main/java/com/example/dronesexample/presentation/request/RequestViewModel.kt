package com.example.dronesexample.presentation.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dronesexample.data.repository.request.RequestRepository
import com.example.dronesexample.data.repository.request.model.ResponseModel
import com.example.dronesexample.data.repository.request.network.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RequestViewModel: ViewModel() {

    private val requestRepository = RequestRepository()

    private val _responseModel = MutableLiveData<ResponseModel>()
    val responseModel = _responseModel as LiveData<ResponseModel>

    private val _responseError = MutableLiveData<Boolean>()
    val responseError = _responseError as LiveData<Boolean>

    fun makeRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            requestRepository.makeRequest { checkResult(it) }
        }
    }

    private fun checkResult(result: Either<ResponseModel>) {
        if (result.param != null) {
            _responseModel.postValue(result.param!!)
        } else _responseError.postValue(result.isResponseError)
    }
}

