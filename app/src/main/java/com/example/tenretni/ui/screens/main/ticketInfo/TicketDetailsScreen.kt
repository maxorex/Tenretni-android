package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.models.Connection
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway
import com.example.tenretni.models.Ticket
import com.example.tenretni.ui.components.TicketCard
import com.example.tenretni.ui.components.TicketBadge
import com.example.tenretni.ui.screens.main.ticketsList.priorityBackgroundColor
import com.example.tenretni.ui.screens.main.ticketsList.statusBackgroundColor

@Composable
fun TicketDetailsScreen(
    ticket: Ticket,
    viewModel: TicketDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(ticket.customer.href) {
        viewModel.startRefreshing(ticket.customer.href)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TicketCard(ticket)

        // Customer Info Section
        val customerResult = uiState.customerResult
        if (customerResult is AsyncResult.Success) {
            CustomerSection(customer = customerResult.data)
        } else {
            CustomerSection(customer = ticket.customer)
        }

        // Gateways Section
        val gateways = if (customerResult is AsyncResult.Success) {
            customerResult.data.gateways
        } else {
            ticket.customer.gateways
        }
        GatewaySection(gateways = gateways)

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // TODO: 5 et 6
            ActionButton(text = "Install", onClick = { /* TODO */ })
            ActionButton(text = "Solve", onClick = { /* TODO */ })
        }
    }
}

@Composable
fun TicketHeader(ticket: Ticket) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Ticket ${ticket.ticketNumber}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ticket.createdDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                TicketBadge(text = ticket.priority, backgroundColor = ticket.priorityBackgroundColor)
                TicketBadge(text = ticket.status, backgroundColor = ticket.statusBackgroundColor)
            }
        }
    }
}

@Composable
fun CustomerSection(customer: Customer) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${customer.firstName} ${customer.lastName}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = customer.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = customer.address,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = customer.city,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                AsyncImage(
                    model = Constants.FLAG_API_URL.format(customer.country.lowercase()),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .width(60.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            
            IconButton(
                onClick = { /* TODO: 4*/ },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(56.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFB3E5FC),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Locate",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun GatewaySection(gateways: List<Gateway>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Gateways",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            gateways.forEach { gateway ->
                GatewayCard(modifier = Modifier.weight(1f), gateway = gateway)
            }
            if (gateways.isEmpty()) {
                Text(text = "No gateways found", modifier = Modifier.padding(16.dp))
            } else if (gateways.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun GatewayCard(modifier: Modifier = Modifier, gateway: Gateway) {
    val isOnline = gateway.connection.status == "Online"
    val statusColor = if (isOnline) Color(0xFF2ECC71) else Color(0xFFFF4757)
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(statusColor)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gateway.connection.status,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            
            if (isOnline) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GatewayInfoRow(icon = Icons.Default.SyncAlt, text = "${gateway.connection.ping.toInt()} ns")
                    GatewayInfoRow(icon = Icons.Default.CloudDownload, text = "%.3f Ebps".format(gateway.connection.download))
                    GatewayInfoRow(icon = Icons.Default.CloudUpload, text = "%.3f Ebps".format(gateway.connection.upload))
                }
            } else {
                Box(
                    modifier = Modifier.height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N/A",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3436)
                    )
                }
            }
            
            Text(
                text = gateway.hash,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun GatewayInfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064)),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .width(150.dp)
            .height(48.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TicketDetailsScreenPreview() {
    val dummyCustomer = Customer(
        firstName = "Risa",
        lastName = "Sloane",
        email = "rsloanehr@paypal.com",
        address = "275 Beilfuss Terrace",
        city = "Campo Formoso",
        country = "br",
        gateways = listOf(
            Gateway(
                href = "",
                serialNumber = "123",
                revision = "1",
                pin = "1111",
                hash = "db6ac1f64ad53d3d",
                customer = Customer(),
                connection = Connection(status = "Online", ping = 16f, download = 22.569f, upload = 3.190f),
                config = com.example.tenretni.models.Config()
            ),
            Gateway(
                href = "",
                serialNumber = "456",
                revision = "1",
                pin = "2222",
                hash = "a06c83449a4cebdd",
                customer = Customer(),
                connection = Connection(status = "Offline"),
                config = com.example.tenretni.models.Config()
            )
        )
    )
    val dummyTicket = Ticket(
        ticketNumber = "9bcbdbf",
        createdDate = "2026-04-25 00:07:00",
        priority = "Critical",
        status = "Open",
        customer = dummyCustomer
    )
    MaterialTheme {
        TicketDetailsScreen(ticket = dummyTicket)
    }
}
