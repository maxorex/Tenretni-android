package com.example.tenretni.ui.screens.main.network

import com.example.tenretni.models.Network
import com.example.tenretni.models.Node

data class NetworkUiState(
    val network: Network? = null,
    val selectedNode: Node? = null
)