package com.example.tenretni.ui.screens.main.gatewayInfo

import androidx.lifecycle.ViewModel
import com.example.tenretni.ui.screens.main.ticketsList.TicketsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GatewayInfoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GatewayInfoUiState())
    val uiState = _uiState.asStateFlow()



}