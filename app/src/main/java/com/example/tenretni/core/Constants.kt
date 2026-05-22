package com.example.tenretni.core

object Constants {

    const val DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    const val ISO_DATETIME_PATTERN_WITHOUT_SECONDS = "yyyy-MM-dd'T'HH:mm:ss"

    const val FLAG_API_URL = "https://flagcdn.com/h40/%s.png"

    object BaseURL {
        private const val BASE_API = "https://api.andromia.science"
        const val TICKETS = "$BASE_API/tickets"
        const val GATEWAYS = "$BASE_API/gateways"
        const val NETWORK = "$BASE_API/network"
        const val CUSTOMER = "$BASE_API/customers"

    }

    object RefreshDelay {
        const val GATEWAY_REFRESH_DELAY = 60000L
        const val CUSTOMER_GATEWAY_REFRESH_DELAY = 45000L
        const val NETWORK_REFRESH_DELAY = 120000L
        const val TICKET_REFRESH_DELAY = 60000L
    }

    enum class TicketPriority {
        Low, Normal, High, Critical
    }

    enum class TicketStatus {
        Open, Solved
    }

    enum class ConnectionStatus {
        Online, Offline
    }


}