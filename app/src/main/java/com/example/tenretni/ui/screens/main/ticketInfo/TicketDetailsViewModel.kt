package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.CustomerRepository
import com.example.tenretni.models.Customer
import com.example.tenretni.models.Ticket
import com.example.tenretni.ui.screens.main.ticketsList.TicketsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import com.example.tenretni.data.repositories.TicketRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch



=======
>>>>>>> e73317496dfb73af48b8358fc3c47cdbc6deae93
class TicketDetailsViewModel : ViewModel() {



    private val _uiState = MutableStateFlow(TicketDetailsUiState())

    val uiState = _uiState.asStateFlow()

<<<<<<< HEAD
//    fun updateTicketStatus(status: String) {
//        _uiState.update { uiState ->
//            val currentTicket = uiState.ticket
//
//            uiState.copy(ticket = currentTicket.copy(status = status))
//        }
//    }

    private var refreshJob: Job? = null
    private val _events = Channel<TicketDetailsEvent>(capacity = Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    private val ticketRepository = TicketRepository()

    private fun installGateway() {
        viewModelScope.launch {

        }
    }
}
