package com.example.tenretni.data.datasources

import ca.qc.cstj.tenretni.core.Constants
import com.example.tenretni.models.Network
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.json.Json

class NetworkDataSource {


    private val json = Json { ignoreUnknownKeys = true }

    fun retrieveAll(): List<Network> {
        val (_, _, result) = Constants.BaseURL.NETWORK.httpGet().responseJson()

        return when (result) {
            is Result.Failure -> throw result.error
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }


}