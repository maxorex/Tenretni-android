package com.example.tenretni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import com.example.tenretni.core.helpers.ColorHelper
import com.example.tenretni.models.Gateway


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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            GatewayListStatus(
                text = when(gateway.connection.status) {
                    "Online" -> stringResource(R.string.status_online)
                    "Offline" -> stringResource(R.string.status_offline)
                    else -> gateway.connection.status
                },
                backgroundColor = ColorHelper.connectionStatusColor(gateway.connection.status)
            )
            if (gateway.connection.status == "Offline") {
                Text(text = stringResource(R.string.n_a))
                Text(text = gateway.serialNumber)
            } else {
                Row {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = Icons.Default.SwapHoriz.toString()
                    )
                    Text(text = gateway.connection.ping.toString()+" ns")
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = Icons.Default.CloudDownload.toString()
                    )
                    Text(text = gateway.connection.download.toString() + " Ebps")
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = Icons.Default.CloudUpload.toString()
                    )
                    Text(text = gateway.connection.upload.toString() + " Ebps")
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