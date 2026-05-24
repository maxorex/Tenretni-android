package com.example.tenretni.ui.screens.main.ticketInfo

import com.example.tenretni.core.AsyncResult
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Ticket

data class TicketDetailsUiState(
    val customerResult: AsyncResult<Customer> = AsyncResult.Loading
)