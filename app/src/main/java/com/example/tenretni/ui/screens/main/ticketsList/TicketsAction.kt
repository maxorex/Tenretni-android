package com.example.tenretni.ui.screens.main.ticketsList

sealed interface TicketsListAction {
    data object RefreshTicket : TicketsListAction

    data class OnSearch(val searchText: String) : TicketsListAction

}