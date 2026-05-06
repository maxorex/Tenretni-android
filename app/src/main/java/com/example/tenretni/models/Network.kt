package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val nextReboot: String,
    val nodes: List<Node>,
    val updateDate: String,
) {
}