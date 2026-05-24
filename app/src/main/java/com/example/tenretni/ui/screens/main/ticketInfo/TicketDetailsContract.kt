package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.annotation.StringRes

sealed interface TicketDetailsAction {

}
sealed interface TicketDetailsEvent {
    data class  OnError(@field:StringRes val errorRes: Int) : TicketDetailsEvent
}