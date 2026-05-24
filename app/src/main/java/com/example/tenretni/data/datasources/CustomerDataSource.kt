package com.example.tenretni.data.datasources

import com.example.tenretni.core.Constants
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.json.Json

class CustomerDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun retrieveAll(): List<Customer> {
        val (_, _, result) = Constants.BaseURL.CUSTOMER.httpGet().responseJson()

        return when (result) {
            is Result.Failure -> throw result.error
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }
}