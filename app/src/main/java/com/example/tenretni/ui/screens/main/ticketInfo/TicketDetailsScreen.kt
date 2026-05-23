package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tenretni.models.Ticket

// TODO C: Les informations des bornes du client doivent être mises à jour automatiquement aux 45 secondes
@Composable
fun TicketDetailsScreen(ticket: Ticket) {
    Text(text = "$ticket")

    // TODO C: Les informations générales du billet sont affichées dans le haut de l’écran. Les mêmes que dans la liste précédente

    // TODO C: Les informations du client associé au billet sont affichées

    // TODO C: Pour chacune des bornes en ligne du client, vous devez afficher les informations suivantes

    // TODO C: Pour chacune des bornes hors ligne, vous devez afficher les informations
}

