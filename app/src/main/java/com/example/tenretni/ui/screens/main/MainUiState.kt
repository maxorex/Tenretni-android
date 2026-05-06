package com.example.tenretni.ui.screens.main

import androidx.navigation3.runtime.NavKey
import ca.qc.cstj.tenretni.core.ui.navigation.BottomBarOptions
import ca.qc.cstj.tenretni.core.ui.navigation.TopBarOptions
import ca.qc.cstj.tenretni.core.ui.navigation.TopLevelBackStack
import com.example.tenretni.ui.navigation.GateWays
import com.example.tenretni.ui.navigation.Network
import com.example.tenretni.ui.navigation.Tickets

data class MainUiState(
    val topLevelBackStack: TopLevelBackStack<NavKey> = TopLevelBackStack(Tickets),
    val topBarOptions: TopBarOptions = TopBarOptions.Defaults,
    val bottomBarOptions: BottomBarOptions = BottomBarOptions(items = listOf(Tickets, GateWays, Network))
)
