package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val status: String,
    val download: Float,
    val ip: String,
    val ping: Float,
    val signal: Float,
    val upload: Float,
)
