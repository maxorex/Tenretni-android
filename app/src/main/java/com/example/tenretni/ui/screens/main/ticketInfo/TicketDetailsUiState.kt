package com.example.tenretni.ui.screens.main.ticketInfo

import com.example.tenretni.core.AsyncResult
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway
import com.example.tenretni.models.Ticket

data class TicketDetailsUiState(
    val ticketResult: AsyncResult<Ticket> = AsyncResult.Loading,
    val installResult: AsyncResult<List<Gateway>> = AsyncResult.Loading,
    val customerResult: AsyncResult<Customer> = AsyncResult.Loading
)
