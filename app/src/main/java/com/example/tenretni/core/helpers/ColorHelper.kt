/*package ca.qc.cstj.tenretni.core.helpers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ca.qc.cstj.tenretni.core.Constants
import ca.qc.cstj.tenretni.ui.theme.GatewayStatusColor
import ca.qc.cstj.tenretni.ui.theme.TicketPriorityColor
import ca.qc.cstj.tenretni.ui.theme.TicketStatusColor

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
            Constants.ConnectionStatus.Online -> GatewayStatusColor.Online
            Constants.ConnectionStatus.Offline -> GatewayStatusColor.Offline
        }

    }

    fun ticketStatusColor(status: String): Color {
        return when (Constants.TicketStatus.valueOf(status)) {
            Constants.TicketStatus.Open -> TicketStatusColor.Open
            Constants.TicketStatus.Solved -> TicketStatusColor.Solved
        }

    }

}*/