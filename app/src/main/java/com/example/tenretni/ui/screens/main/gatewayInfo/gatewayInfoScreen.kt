package com.example.tenretni.ui.screens.main.gatewayInfo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tenretni.R
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.helpers.ColorHelper
import com.example.tenretni.models.Gateway
import com.example.tenretni.ui.components.DetailRow


@Composable
fun GatewayInfoScreen(
    viewModel: GatewayInfoViewModel = viewModel(),
    gateway: Gateway
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(gateway.serialNumber) {
        viewModel.refreshGateway(gateway.serialNumber)
    }

    val gateway = when (val result = uiState.gatewayResult) {
        is AsyncResult.Success -> result.data
        else -> gateway
    }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitMode(
            gateway = gateway
        )
    } else {
        LandscapeMode(
            gateway = gateway
        )
    }
}

@Composable
private fun PortraitMode(
    gateway: Gateway
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Info(gateway)

        Spacer(modifier = Modifier.height(20.dp))

        // Stats
        StatsCard(gateway)

        Spacer(modifier = Modifier.height(24.dp))

        // Icons Row
        IconsRow(gateway)

        Spacer(modifier = Modifier.height(16.dp))

        // Color Bar
        ColorBar(gateway.hash)

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Buttons()
    }
}


@Composable
private fun LandscapeMode(
    gateway: Gateway
) {

}

@Composable
private fun Info(
    gateway: Gateway
) {
    // Status
    Surface(
        color = ColorHelper.connectionStatusColor(gateway.connection.status),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = gateway.connection.status,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Serial Number
    Text(
        text = gateway.serialNumber,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

    // Config Info
    Text(
        text = "MAC: ${gateway.config.mac}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "SSID: ${gateway.config.SSID}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "PIN: ${gateway.pin}",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun IconsRow(
    gateway: Gateway
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

    }
}

@Composable
private fun StatsCard(
    gateway: Gateway
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        if (gateway.connection.status == "Online") {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = gateway.connection.ip,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                DetailRow(
                    icon = Icons.Default.SwapHoriz,
                    value = "${gateway.connection.ping} ns"
                )
                DetailRow(
                    icon = Icons.Default.CloudDownload,
                    value = "${gateway.connection.download} Ebps"
                )
                DetailRow(
                    icon = Icons.Default.CloudUpload,
                    value = "${gateway.connection.upload} Ebps"
                )
                DetailRow(
                    icon = Icons.Default.SignalCellularAlt,
                    value = "${gateway.connection.signal} dBm"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            R.string.kernel_version,
                            gateway.config.kernelRevision.toInt()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.version, gateway.config.version),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Text(
                text = "N/A",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
private fun ColorBar(
    hash : String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

    }
}

@Composable
private fun Buttons(

) {
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

