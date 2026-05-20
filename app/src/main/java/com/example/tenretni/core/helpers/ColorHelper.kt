package com.example.tenretni.core.helpers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.tenretni.core.Constants
import com.example.tenretni.ui.theme.ConnectionStatusColor
import com.example.tenretni.ui.theme.TicketPriorityColor
import com.example.tenretni.ui.theme.TicketStatusColor

val String.toColor
    get() = Color(this.toColorInt())

object ColorHelper {

    fun ticketPriorityColor(priority: String): Color {
        return when (Constants.TicketPriority.valueOf(priority)) {
            Constants.TicketPriority.Low -> TicketPriorityColor.Low
            Constants.TicketPriority.Normal -> TicketPriorityColor.Normal
            Constants.TicketPriority.High -> TicketPriorityColor.High
            Constants.TicketPriority.Critical -> TicketPriorityColor.Critical
        }

    }

    fun connectionStatusColor(status: String): Color {
        return when (Constants.ConnectionStatus.valueOf(status)) {
            Constants.ConnectionStatus.Online -> ConnectionStatusColor.Online
            Constants.ConnectionStatus.Offline -> ConnectionStatusColor.Offline
        }

    }

    fun ticketStatusColor(status: String): Color {
        return when (Constants.TicketStatus.valueOf(status)) {
            Constants.TicketStatus.Open -> TicketStatusColor.Open
            Constants.TicketStatus.Solved -> TicketStatusColor.Solved
        }

    }

}