package com.example.tenretni.ui.navigation

interface Route {

    data object  ToTitleScreen : Route
    data object toMainScreen : Route

//    Might remove toTicketScreen later
    data object ToTicketsScreen : Route

    data object ToGateWaysScreen : Route

    data object ToNetworkScreen : Route
}