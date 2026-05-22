package com.example.tenretni.data.datasources

import com.example.tenretni.core.Constants
import com.example.tenretni.models.Gateway
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.json.Json

class GatewayDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun retrieveAll(): List<Gateway> {
        val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()

        return when (result) {
            is Result.Failure -> throw result.error
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }
}