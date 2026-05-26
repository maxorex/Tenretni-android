package com.example.tenretni.ui.screens.main.GatewaysList


sealed interface GatewaysListAction {
    data object RefreshGateways : GatewaysListAction
    data class OnSearch(val searchText: String) : GatewaysListAction
}