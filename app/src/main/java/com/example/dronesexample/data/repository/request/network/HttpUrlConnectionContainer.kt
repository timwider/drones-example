package com.example.dronesexample.data.repository.request.network



import com.example.dronesexample.data.repository.request.model.ResponseModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object HttpUrlConnectionContainer {

    fun makeGetRequest(urlValue: String, block: (Either<ResponseModel>) -> Unit) {
        val url = URL(urlValue)
        val gson = Gson()

        val result: Either<ResponseModel> =
            try {
                val urlConnection = url.openConnection() as HttpURLConnection
                val reader = BufferedReader(urlConnection.inputStream.reader())
                val content = reader.readText()
                val responseModel = gson.fromJson(content, ResponseModel::class.java)
                reader.close()
                urlConnection.disconnect()
                Either(param = responseModel)
        } catch (e: Exception) {
            Either()
        }
        block.invoke(result)
    }
}

class Either<T> (val param: T? = null) { val isResponseError = true }