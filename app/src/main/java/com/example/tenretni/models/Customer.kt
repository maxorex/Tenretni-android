package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val href: String,
    val firstName: String,
    val lastName: String,
    val coord: Coordinate,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val phone: String,
    val gateways: List<Gateway>
) {
}

@Serializable
data class Coordinate(
    val latitude: Float,
    val longitude:Float
)