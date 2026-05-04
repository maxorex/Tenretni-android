package com.example.tenretni.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ca.qc.cstj.tenretni.core.ui.navigation.BottomNavItem
import ca.qc.cstj.tenretni.core.ui.navigation.Screen
import com.example.tenretni.R

data object Tickets: BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.tickets)
    override val title: String
        @Composable get() = "tickets"
}

data object GateWays : BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.gateway)
    override val title: String
        @Composable get() = "gate ways"
}

data object Network : BottomNavItem, Screen {
    override val icon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.network)
    override val title: String
        @Composable get() = "network"
}