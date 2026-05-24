package com.example.tenretni.ui.screens.main.gatewayInfo

import com.example.tenretni.core.AsyncResult
import com.example.tenretni.models.Gateway

data class GatewayInfoUiState(
    val gatewayResult: AsyncResult<Gateway> = AsyncResult.Loading,
    val isRefreshing: Boolean = false
)