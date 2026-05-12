package com.example.tenretni.ui.screens.main.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NetworkScreen(

)  {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ){
        Column(modifier = Modifier.fillMaxSize(0.5f)){
            Text(
                text = "Vous devez afficher le logo dans le haut de l’écran"
            )
            Text(
                text = "Next reboot at: 2026-04-25 01:40:00"
            )
            Text(
                text = "Last update: 2026-04-25 01:35:00"
            )
            Text(
                text = "Node"
            )
            Text(
                text = "Selected node"
            )
        }

    }


}