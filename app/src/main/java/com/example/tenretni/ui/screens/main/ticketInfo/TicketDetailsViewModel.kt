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
import com.example.tenretni.R
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.models.Gateway
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.collections.find

class TicketDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TicketDetailsUiState())

    val uiState = _uiState.asStateFlow()

    private var refreshJob: Job? = null
    private val _events = Channel<TicketDetailsEvent>(capacity = Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    private val ticketRepository = TicketRepository()
    private val customerRepository = CustomerRepository()

    fun startRefreshing(customerHref: String) {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                customerRepository.retrieveAll()
                    .flowOn(Dispatchers.IO)
                    .catch { ex ->
                        _uiState.update { it.copy(customerResult = AsyncResult.Error(ex.hashCode())) }
                    }
                    .collect { customers ->
                        val customer = customers.find { it.href == customerHref }
                        if (customer != null) {
                            _uiState.update { it.copy(customerResult = AsyncResult.Success(customer)) }
                        } else {
                            _uiState.update { it.copy(customerResult = AsyncResult.Error(0)) }
                        }
                    }
                delay(Constants.RefreshDelay.CUSTOMER_GATEWAY_REFRESH_DELAY)
            }
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

    fun updateTicketStatus(status: String, ticketId: String) {
        viewModelScope.launch {
            ticketRepository.updateStatus(ticketId, status)
                .flowOn(Dispatchers.IO)
                .catch { ex ->
                    _uiState.update { it.copy(ticketResult = AsyncResult.Error(ex.hashCode())) }
                }
                .collect { updatedTicket ->
                    _uiState.update { it.copy(ticketResult = AsyncResult.Success(updatedTicket)) }
                }
        }
    }

    fun onAction(action: TicketDetailsAction){
        when(action){
            is TicketDetailsAction.Install -> installGateway(action.qrContent)
            is TicketDetailsAction.Update -> updateTicketStatus(action.status, action.ticketId)
            TicketDetailsAction.Refresh -> TODO()
        }
    }
}
