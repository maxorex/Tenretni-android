package com.example.tenretni.ui.screens.main.ticketsList

import com.example.tenretni.models.Ticket

sealed interface TicketsListAction {
    data object RefreshTicket : TicketsListAction

    data class OnSearch(val searchText: String) : TicketsListAction

}