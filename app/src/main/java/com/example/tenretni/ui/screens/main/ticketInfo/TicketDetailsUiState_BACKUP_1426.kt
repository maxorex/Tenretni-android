package com.example.tenretni.ui.screens.main.ticketInfo

import com.example.tenretni.core.AsyncResult
<<<<<<< HEAD
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Ticket

data class TicketDetailsUiState(
    val customerResult: AsyncResult<Customer> = AsyncResult.Loading
)
=======
import com.example.tenretni.models.Gateway

data class TicketDetailsUiState(
    val installResult: AsyncResult<List<Gateway>> = AsyncResult.Loading
)
>>>>>>> e73317496dfb73af48b8358fc3c47cdbc6deae93
