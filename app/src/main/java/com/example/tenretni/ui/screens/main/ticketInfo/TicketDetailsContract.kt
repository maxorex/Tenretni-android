package com.example.tenretni.ui.screens.main.ticketInfo

import android.R
import androidx.annotation.StringRes
import com.example.tenretni.models.Gateway

sealed interface TicketDetailsAction {

    data class Install(val qrContent: String?) : TicketDetailsAction

    data object Refresh : TicketDetailsAction

}
sealed interface TicketDetailsEvent {
    data class OnError(@field:StringRes val errorRes: Int) : TicketDetailsEvent
    data object OnInstallSuccess : TicketDetailsEvent
}