package com.example.tenretni.ui.screens.main.GatewaysList

import com.example.tenretni.models.Gateway


sealed interface GatewaysListAction {
    data object RefreshGateways : GatewaysListAction
    data class OnSearch(val searchText: String) : GatewaysListAction

    data class OpenGatewayDetails(val gateway: Gateway) : GatewaysListAction
}