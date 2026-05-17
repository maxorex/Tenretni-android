package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val href: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val coord: Coordinate? = null,
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val gateways: List<Gateway> = emptyList()
) {
}

@Serializable
data class Coordinate(
    val latitude: Float = 0f,
    val longitude: Float = 0f
)