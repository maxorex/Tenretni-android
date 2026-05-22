package com.example.tenretni.ui.screens.main.ticketsList

import androidx.compose.ui.graphics.Color
import com.example.tenretni.models.Ticket

val Ticket.priorityColors: Triple<Color, Color, Color> get() = when (this.priority) {
        "Critical" -> Triple(Color(0xFFC63728), Color(0xFFC63728), Color(0xFFC63728))
        "High" -> Triple(Color(0xFFE67E00), Color(0xFFE67E00), Color(0xFFE67E00))
        "Normal" -> Triple(Color(0xFFFFC800), Color(0xFFFFC800), Color(0xFFFFC800))
        "Low" -> Triple(Color(0xFF008000), Color(0xFF008000), Color(0xFF008000))
        else -> Triple(Color.Gray, Color.Gray, Color.Gray)
    }

val Ticket.priorityBackgroundColor: Color get() = this.priorityColors.first

val Ticket.statusColors: Triple<Color, Color, Color> get() = when (this.status) {
        "Open" -> Triple(Color(0xFF7B61FF), Color(0xFF7B61FF), Color(0xFF7B61FF))
        "Solved" -> Triple(Color(0xFF006400), Color(0xFF006400), Color(0xFF006400))
        else -> Triple(Color.Gray, Color.Gray, Color.Gray)
    }

val Ticket.statusBackgroundColor: Color get() = this.statusColors.first
