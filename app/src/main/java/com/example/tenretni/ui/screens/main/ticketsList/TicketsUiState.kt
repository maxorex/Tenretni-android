package com.example.tenretni.ui.screens.main.ticketsList

import ca.qc.cstj.tenretni.core.AsyncResult
import com.example.tenretni.models.Ticket

data class TicketsUiState(
    val ticketResult: AsyncResult<List<Ticket>> = AsyncResult.Loading,
    val isRefreshing: Boolean = false,
    val searchText: String = ""
)