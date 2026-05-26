package com.example.tenretni.ui.screens.main.ticketInfo

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tenretni.R
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.core.extensions.ObserveAsEvents
import com.example.tenretni.core.extensions.OnResume
import com.example.tenretni.core.helpers.ColorHelper
import com.example.tenretni.core.ui.components.ErrorMessage
import com.example.tenretni.core.ui.components.LoadingAnimation
import com.example.tenretni.core.ui.navigation.TopBarOptions
import com.example.tenretni.models.Connection
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway
import com.example.tenretni.models.Ticket
import com.example.tenretni.ui.components.CustomerCard
import com.example.tenretni.ui.components.GatewayListCard
import com.example.tenretni.ui.components.TicketBadge
import com.example.tenretni.ui.components.TicketCard

import com.google.android.gms.maps.model.LatLng
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.BarcodeFormat
import io.github.g00fy2.quickie.config.ScannerConfig

@Composable
fun TicketDetailsScreen(
    ticket: Ticket,
    viewModel: TicketDetailsViewModel = viewModel(),
    toMapScreen: (LatLng) -> Unit,
    onGatewayClick: (Gateway) -> Unit,
    onUpdateTopBar: (TopBarOptions) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onUpdateTopBar(
            TopBarOptions(
                isTopBarVisible = true,
                isBackButtonVisible = true,
                titleStringRes = R.string.ticket,
                titleArgs = listOf(ticket.ticketNumber)
            )
        )
    }

    OnResume {
        viewModel.onAction(TicketDetailsAction.Refresh(ticket.customer.href))
    }

    val currentTicket =
        when (val result = uiState.ticketResult) {
            is AsyncResult.Success -> result.data
            else -> ticket
        }

    LaunchedEffect(ticket.customer.href) {
        viewModel.startRefreshing(ticket.customer.href)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is TicketDetailsEvent.OnError -> Toast.makeText(
                context,
                event.errorRes,
                Toast.LENGTH_LONG
            ).show()

            is TicketDetailsEvent.OnSuccess -> Toast.makeText(
                context,
                event.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val scanQrCodeLauncher = rememberLauncherForActivityResult(ScanCustomCode()) { qrResult ->
        when (qrResult) {
            is QRResult.QRError -> {
                Toast.makeText(
                    context,
                    R.string.error_while_scanning_qr_code, Toast.LENGTH_LONG
                ).show()
            }

            QRResult.QRMissingPermission -> {
                Toast.makeText(
                    context,
                    R.string.missing_permission, Toast.LENGTH_LONG
                ).show()
            }

            is QRResult.QRSuccess -> {
                viewModel.onAction(
                    TicketDetailsAction.Install(
                        ticket.customer.href,
                        qrResult.content.rawValue
                    )
                )
            }
            QRResult.QRUserCanceled -> {}
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TicketCard(currentTicket)

        // Customer Info Section
        val customerResult = uiState.customerResult
        if (customerResult is AsyncResult.Success) {
            CustomerCard(customer = customerResult.data, onLocationClick = {
                customerResult.data.coord?.let {
                    toMapScreen(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                }
            })
        } else {
            CustomerCard(customer = currentTicket.customer, onLocationClick = {
                currentTicket.customer.coord?.let {
                    toMapScreen(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                }
            })
        }

        // Gateways Section
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (val customerGateways = uiState.customerGateways) {
                is AsyncResult.Error -> ErrorMessage(customerGateways.messageResId)
                AsyncResult.Loading -> LoadingAnimation()
                is AsyncResult.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(customerGateways.data) { gateway ->
                            GatewayListCard(
                                gateway = gateway,
                                onClick = {
                                    onGatewayClick(gateway)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(text = stringResource(R.string.install), onClick = {
                scanQrCodeLauncher.launch(
                    ScannerConfig.build {
                        setBarcodeFormats(listOf(BarcodeFormat.FORMAT_ALL_FORMATS))
                        setOverlayStringRes(R.string.scan_the_id)
                        setShowCloseButton(true)
                    }
                )

            })
            if (currentTicket.status == "Open") {
                ActionButton(text = stringResource(R.string.solve), onClick = {
                    viewModel.onAction(TicketDetailsAction.Update("solve", currentTicket.href))
                })
            } else {
                ActionButton(text = stringResource(R.string.open), onClick = {
                    viewModel.onAction(TicketDetailsAction.Update("open", currentTicket.href))
                })
            }
        }
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
                connection = Connection(
                    status = "Online",
                    ping = 16f,
                    download = 22.569f,
                    upload = 3.190f
                ),
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
        TicketDetailsScreen(ticket = dummyTicket, toMapScreen = {}, onGatewayClick = {})
    }
}
