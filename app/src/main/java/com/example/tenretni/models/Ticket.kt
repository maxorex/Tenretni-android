package com.example.tenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val ticketNumber: String = "",
    val createdDate: String = "",
    val priority: String = "",
    val status: String = "",
    val customer: Customer = Customer(),
    val href: String = ""
)

