package com.example.tenretni.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.tenretni.core.ui.navigation.BottomNavItem
import com.example.tenretni.core.ui.navigation.Screen
import com.example.tenretni.R

data object Tickets: BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.tickets)
    override val title: String
        @Composable get() = "Tickets"
}

data object GateWays : BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.gateway)
    override val title: String
        @Composable get() = "Gateways"
}

data object Network : BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.network)
    override val title: String
        @Composable get() = "Network"
}