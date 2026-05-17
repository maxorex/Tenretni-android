package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val status: String = "",
    val download: Float = 0f,
    val ip: String = "",
    val ping: Float = 0f,
    val signal: Float = 0f,
    val upload: Float = 0f,
)
