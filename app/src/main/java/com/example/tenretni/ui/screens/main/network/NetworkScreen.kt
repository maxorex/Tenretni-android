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
import com.example.tenretni.ui.components.NodeDetailsCard

// TODO C: Les données sur l’état global du réseau doivent être mises à jour automatique à chaque 2 minutes
// TODO C: Changer pour ajouter la bd
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

        // TODO C: Rendre plus grand
        // Logo
        Image(
            painter = painterResource(id = R.drawable.tenretni),
            contentDescription = "Tenretni Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 16.dp)
        )

        // TODO C: vérifier pour bon format
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

        // TODO C: rendre mieu je crois
        // Node List (Horizontal)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {

            items(uiState.network?.nodes ?: emptyList()) { node ->

                NodeCard(node, onNodeClick = { viewModel.selectNode(node) }, uiState.selectedNode == node)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // NodeDetailsCard
        // (Si selectedNode pas null)
        uiState.selectedNode?.let { node ->
            NodeDetailsCard(node)
        }
    }
}
