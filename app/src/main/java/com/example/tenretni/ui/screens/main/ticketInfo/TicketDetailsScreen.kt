package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tenretni.models.Ticket

@Composable
fun TicketDetailsScreen(ticket: Ticket) {
    Text(text = "$ticket")
}

