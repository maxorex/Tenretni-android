package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.annotation.StringRes
import com.example.tenretni.models.Gateway

sealed interface TicketDetailsAction {

    data class  Install(val qrContent: Gateway?) : TicketDetailsAction

    data class Update(val status: String, val ticketId: String) : TicketDetailsAction
    data object Refresh : TicketDetailsAction


}
sealed interface TicketDetailsEvent {
    data class  OnError(@field:StringRes val errorRes: Int) : TicketDetailsEvent
}