package com.example.tenretni.ui.screens.main.ticketInfo

import com.example.tenretni.core.AsyncResult
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway

data class TicketDetailsUiState(
    val installResult: AsyncResult<Gateway> = AsyncResult.Loading,
    val customerResult: AsyncResult<Customer> = AsyncResult.Loading
)
