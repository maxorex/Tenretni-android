package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.annotation.StringRes

sealed interface TicketDetailsAction {

    data class Install(val customerHref: String?,val qrContent: String?) : TicketDetailsAction

    data class Refresh(val customerHref: String) : TicketDetailsAction

}
sealed interface TicketDetailsEvent {
    data class OnError(@field:StringRes val errorRes: Int) : TicketDetailsEvent
}