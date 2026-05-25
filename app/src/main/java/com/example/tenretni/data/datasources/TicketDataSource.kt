package com.example.tenretni.data.datasources

import com.example.tenretni.core.Constants
import com.example.tenretni.models.Ticket
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.json.Json

class TicketDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    fun retrieveAll(): List<Ticket> {
        val (_, _, result) = Constants.BaseURL.TICKETS.httpGet().responseJson()

        return when(result) {
            is Result.Failure -> throw result.error
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }

    fun updateStatus(ticketId: String, status: String): Ticket {
        val (_, _, result) = "${Constants.BaseURL.TICKETS}/$ticketId/actions"
            .httpPost(listOf("type" to status)).responseJson()

        return when(result) {
            is Result.Failure -> throw result.error
            is Result.Success -> json.decodeFromString(result.value.content)
        }
    }

}