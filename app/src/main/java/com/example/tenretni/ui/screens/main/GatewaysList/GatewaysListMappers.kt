package com.example.tenretni.ui.screens.main.GatewaysList

import androidx.compose.ui.graphics.Color
import com.example.tenretni.models.Gateway

val Gateway.statusColors: Color
    get() = when (this.connection.status) {
        "Online" -> Color.Green
        "Offline" -> Color.Red
        else -> {
            Color.Gray
        }
    }