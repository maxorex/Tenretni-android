package com.example.tenretni.ui.screens.main.gatewayInfo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tenretni.models.Config
import com.example.tenretni.models.Connection
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Gateway
import com.example.tenretni.ui.screens.main.network.DetailRow
import com.example.tenretni.ui.theme.TenretniTheme


@Composable
fun GatewayInfoScreen(
    viewModel: GatewayInfoViewModel = viewModel(),
    gateway: Gateway
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitMode(
            uiState = uiState,
            gateway = gateway
        )
    } else {
        LandscapeMode(
            uiState = uiState,
            gateway = gateway
        )
    }
}

@Composable
private fun PortraitMode(
    uiState: GatewayInfoUiState,
    gateway: Gateway
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Online Badge
        Surface(
            color = Color(0xFF2ECC71),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Online",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 6.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Serial Number
        Text(
            text = "db6ac1f64ad53d3d",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Config Info
        Text(text = "MAC: 62:71:48:b5:be:b0", style = MaterialTheme.typography.bodyLarge)
        Text(text = "SSID: solid_state.bandwidth.PNG", style = MaterialTheme.typography.bodyLarge)
        Text(text = "PIN: 64fe75fc", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Stats Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            border = CardDefaults.outlinedCardBorder().copy(
                width = 0.5.dp,
                brush = androidx.compose.ui.graphics.SolidColor(Color.LightGray.copy(alpha = 0.5f))
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "246.160.160.97",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                DetailRow(icon = Icons.Default.SwapHoriz, value = "16 ns")
                DetailRow(icon = Icons.Default.CloudDownload, value = "22.569 Ebps")
                DetailRow(icon = Icons.Default.CloudUpload, value = "3.190 Ebps")
                DetailRow(icon = Icons.Default.SignalCellularAlt, value = "-28 dBm")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Kernel revision 1",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Version 1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Icons Row (Placeholders)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Color Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "27", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(4.dp))
            val colors = listOf(
                Color(0xFFD2D2A0), Color(0xFF4A5D23), Color(0xFF32CD32),
                Color(0xFF6B8E23), Color(0xFF483D8B), Color(0xFF00FF7F),
                Color(0xFFADFF2F), Color(0xFF3CB371), Color(0xFF00FA9A),
                Color(0xFF4169E1)
            )
            Row(modifier = Modifier.weight(1f).height(16.dp)) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(color)
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "dd", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Update", color = Color.White)
            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Reboot", color = Color.White)
            }
        }
    }
}


@Composable
private fun LandscapeMode(
    uiState: GatewayInfoUiState,
    gateway: Gateway
) {

}

@Preview(showBackground = true)
@Composable
fun PortraitModePreview() {
    TenretniTheme {
        PortraitMode(
            uiState = GatewayInfoUiState(),
            gateway = Gateway(
                href = "",
                serialNumber = "db6ac1f64ad53d3d",
                revision = "",
                pin = "64fe75fc",
                hash = "",
                customer = Customer(firstName = "", lastName = "", email = "", city = "", country = "", href = ""),
                connection = Connection(
                    status = "Online",
                    download = 22.569f,
                    ip = "246.160.160.97",
                    ping = 16f,
                    signal = -28f,
                    upload = 3.190f
                ),
                config = Config(
                    mac = "62:71:48:b5:be:b0",
                    SSID = "solid_state.bandwidth.PNG",
                    version = "1.0.0",
                    kernelRevision = 1f
                )
            )
        )
    }
}
