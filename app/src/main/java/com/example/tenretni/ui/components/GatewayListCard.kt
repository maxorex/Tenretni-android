package com.example.tenretni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tenretni.R
import com.example.tenretni.models.Gateway
import com.example.tenretni.ui.screens.main.GatewaysList.statusColors

@Composable
fun GatewayListCard(
    gateway: Gateway,
    onClick: (Gateway) -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .width(300.dp)
            .clickable { onClick(gateway) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            GatewayListStatus(
                text = gateway.connection.status,
                backgroundColor = gateway.statusColors
            )
            if (gateway.connection.status == "Offline") {
                Text(text = stringResource(R.string.n_a))
                Text(text = gateway.serialNumber)
            } else {
                Row() {
                    // Icon here
                    Text(text = gateway.pin)
                }
                Row() {
                    // icon here
                    Text(text = gateway.connection.download.toString())
                }
                Row() {
                    // icon here
                    Text(text = gateway.connection.upload.toString())
                }
                Text(text = gateway.serialNumber)
            }
        }
    }
}

// Repris cette fontion fait par Mathias
@Composable
fun GatewayListStatus(
    text: String,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
    }
}