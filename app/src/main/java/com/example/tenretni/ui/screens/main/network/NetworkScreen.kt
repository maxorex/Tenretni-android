package com.example.tenretni.ui.screens.main.network

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tenretni.R
import com.example.tenretni.ui.components.NodeCard

// TODO: Les données sur l’état global du réseau doivent être mises à jour automatique à chaque 2 minutes
// TODO: Changer pour ajouter la bd
@Composable
fun NetworkScreen(
    viewModel: NetworkViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        // TODO: Rendre plus grand
        // Logo
        Image(
            painter = painterResource(id = R.drawable.tenretni),
            contentDescription = "Tenretni Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 16.dp)
        )

        // TODO: vérifier pour bon format
        // Reboot and Update Info
        Text(
            text = "Next reboot at: ${uiState.network?.nextReboot ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Last update: ${uiState.network?.updateDate ?: "Loading..."}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: rendre mieu je crois
        // Node List (Horizontal)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {

            items(uiState.network?.nodes ?: emptyList()) { node ->

                NodeCard(node)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Utiliser la node sélectionner
        // TODO: Faire NodeDetailsCard
        // Selected Node Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp, brush = androidx.compose.ui.graphics.SolidColor(Color.LightGray.copy(alpha = 0.5f)))
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Lukryx",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

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

                Text(
                    text = "3.3.3.3",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                DetailRow(icon = Icons.Default.SwapHoriz, value = "6 ns")
                DetailRow(icon = Icons.Default.CloudDownload, value = "115.708 Ebps")
                DetailRow(icon = Icons.Default.CloudUpload, value = "90.509 Ebps")
                DetailRow(icon = Icons.Default.SignalCellularAlt, value = "-20 dBm")
            }
        }
    }
}


// TODO: A vérifier
@Composable
fun DetailRow(icon: ImageVector, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}
