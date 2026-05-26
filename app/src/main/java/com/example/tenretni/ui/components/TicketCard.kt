package com.example.tenretni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.tenretni.core.helpers.ColorHelper
import com.example.tenretni.models.Ticket

@Composable
fun TicketCard(
    ticket: Ticket,
    onClick: (Ticket) -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick(ticket)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.ticket,ticket.ticketNumber ),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = ticket.createdDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                TicketBadge(
                    text = when(ticket.priority) {
                        "Low" -> stringResource(R.string.priority_low)
                        "Normal" -> stringResource(R.string.priority_normal)
                        "High" -> stringResource(R.string.priority_high)
                        "Critical" -> stringResource(R.string.priority_critical)
                        else -> ticket.priority
                    },
                    backgroundColor = ColorHelper.ticketPriorityColor(ticket.priority)
                )
                TicketBadge(
                    text = when(ticket.status) {
                        "Open" -> stringResource(R.string.status_open)
                        "Solved" -> stringResource(R.string.status_solved)
                        else -> ticket.status
                    },
                    backgroundColor = ColorHelper.ticketStatusColor(ticket.status)
                )
            }
        }
    }
}

@Composable
fun TicketBadge(
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
            style = MaterialTheme.typography.labelLarge
        )
    }
}
