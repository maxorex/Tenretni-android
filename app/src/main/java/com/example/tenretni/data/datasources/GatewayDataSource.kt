package com.example.tenretni.data.datasources

import com.example.tenretni.core.Constants
import com.example.tenretni.models.Gateway
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
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

    fun retrieveCustomerGateways(customerId: String): List<Gateway> {
        val url = "${customerId}/gateways"

        val (_, _, result) = url.httpGet().responseJson()

        return when (result) {
            is Result.Failure -> throw result.getException().exception
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }


    fun installCustomerGateway(customerId: String?, gatewayInfo: String): String {
        val url = "${customerId}/gateways"

        val (_, _, result) = url.httpPost().jsonBody(gatewayInfo).responseJson()

        return when (result) {
            is Result.Failure -> throw result.getException().exception
            is Result.Success -> (result.value.content)
        }
    }
}