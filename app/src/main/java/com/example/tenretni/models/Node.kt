package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val name: String,
    val connection: Connection,
) {
}