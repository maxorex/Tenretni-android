package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Gateway(
    val href: String,
    val serialNumber: String,
    val revision: String,
    val pin: String,
    val hash: String,
    val customer: Customer,
    val connection: Connection,
    val config: Config,

) {
}



@Serializable
data class Config(
    val kernel: List<String>,
    val mac: String,
    val SSID: String,
    val version: String,
    val kernelRevision: Float,
    val installDate: String,
)