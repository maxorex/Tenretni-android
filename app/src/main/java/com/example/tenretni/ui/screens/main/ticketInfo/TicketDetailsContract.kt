package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.annotation.StringRes

sealed interface TicketDetailsAction {

    data class Install(val customerHref: String?,val qrContent: String?) : TicketDetailsAction

    data class Update(val status: String, val ticketId: String) : TicketDetailsAction
    data object Refresh : TicketDetailsAction


}
sealed interface TicketDetailsEvent {
    data class OnError(@field:StringRes val errorRes: Int) : TicketDetailsEvent
}