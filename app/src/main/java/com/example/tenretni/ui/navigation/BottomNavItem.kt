package com.example.tenretni.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ca.qc.cstj.tenretni.core.ui.navigation.BottomBarOptions
import ca.qc.cstj.tenretni.core.ui.navigation.BottomNavItem
import ca.qc.cstj.tenretni.core.ui.navigation.Screen
import ca.qc.cstj.tenretni.core.ui.navigation.TopBarOptions
import com.example.tenretni.R
import com.example.tenretni.models.Ticket


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

data class TicketDetail(val ticket: Ticket) : Screen {
    override val topBarOptions: TopBarOptions
        get() = TopBarOptions(
            isTopBarVisible = true,
            isBackButtonVisible = true
        )
    override val bottomBarOptions: BottomBarOptions
        get() = BottomBarOptions(isBottomBarVisible = false)
}

