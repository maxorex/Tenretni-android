package com.example.tenretni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tenretni.core.helpers.ColorHelper
import com.example.tenretni.models.Node
import com.example.tenretni.ui.theme.CardColor
import com.example.tenretni.ui.theme.ConnectionStatusColor

// TODO C: A vérifier + rendre plus clean
// TODO C: Fix toute les couleur
@Composable
fun NodeCard(
    node: Node,
    onNodeClick: (Node) -> Unit = {},
    isSelected: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CardColor.Selected else CardColor.Default
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.width(220.dp)
            .clickable {
                onNodeClick(node)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = node.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = node.connection.ip,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Surface(
                // TODO C: Utiliser les couleur et le texte traduit
                color = ColorHelper.connectionStatusColor(node.connection.status),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = node.connection.status,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NodeDetailsCard(node: Node){
    // TODO C: Utiliser la node sélectionner
    // Selected Node Details Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor.Default),
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
                text = node.name,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            // TODO C: Couleur
            Surface(
                color = ColorHelper.connectionStatusColor(node.connection.status),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = node.connection.status,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 48.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Text(
                text = node.connection.ip,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailRow(icon = Icons.Default.SwapHoriz, value = "${node.connection.ping} ns")
            DetailRow(icon = Icons.Default.CloudDownload, value = "${node.connection.download} Ebps")
            DetailRow(icon = Icons.Default.CloudUpload, value = "${node.connection.upload} Ebps")
            DetailRow(icon = Icons.Default.SignalCellularAlt, value = "${node.connection.signal} dBm")
        }
    }
}

// TODO C: A vérifier
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