package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tenretni.models.Ticket
import com.example.tenretni.R


@Composable
fun TicketDetailsScreen(ticket: Ticket, toMapScreen: () -> Unit ) {



}

@Composable
fun LocationButton(toMapScreen: () -> Unit) {
    Button(
        onClick = toMapScreen,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.MyLocation,
            contentDescription = stringResource(R.string.my_location),
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}