package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.R
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.models.Gateway
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TicketDetailsViewModel : ViewModel() {



    private val _uiState = MutableStateFlow(TicketDetailsUiState())

    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TicketDetailsEvent>(capacity = Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    private val ticketRepository = TicketRepository()

    private fun startRefreshing(href: String) {
        viewModelScope.launch {
            // TODO:
        }
    }

    private fun installGateway(rawQr: Gateway?) {
        viewModelScope.launch {
            if(rawQr == null){
                _events.send(TicketDetailsEvent.OnError(R.string.qr_code_error))
                return@launch
            }

//            val gateway = Gateway()
        }
    }

    fun onAction(action: TicketDetailsAction){
        when(action){
            is TicketDetailsAction.Install -> installGateway(action.qrContent)
            TicketDetailsAction.Refresh -> TODO()
        }
    }
}
