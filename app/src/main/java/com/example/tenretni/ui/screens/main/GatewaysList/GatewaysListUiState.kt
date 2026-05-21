package com.example.tenretni.ui.screens.main.GatewaysList

import com.example.tenretni.core.AsyncResult
import com.example.tenretni.models.Gateway

data class GatewaysListUiState(
    val gatewayResult: AsyncResult<List<Gateway>> = AsyncResult.Loading,
    val isRefreshing: Boolean = false,
    val searchText: String = ""

)