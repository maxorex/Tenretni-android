package com.example.tenretni.ui.screens.main.ticketInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.R
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.CustomerRepository
import com.example.tenretni.data.repositories.GatewayRepository
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.models.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TicketDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TicketDetailsUiState())

    val uiState = _uiState.asStateFlow()

    private var refreshJob: Job? = null
    private val _events = Channel<TicketDetailsEvent>(capacity = Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    private val ticketRepository = TicketRepository()
    private val customerRepository = CustomerRepository()

    private val gatewayRepository = GatewayRepository()

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


    private fun installGateway(rawQr: String?) {
        viewModelScope.launch {
            if (rawQr == null) {
                _events.send(TicketDetailsEvent.OnError(R.string.qr_code_error))
                return@launch
            }

//            gatewayRepository.installCustomerGateway()

//            val customerGateway =



        }
    }

    //    fun updateTicketStatus(status: String) {
//        _uiState.update { uiState ->
//            val currentTicket = uiState.ticket
//
//            uiState.copy(ticket = currentTicket.copy(status = status))
//        }
//    }

    fun onAction(action: TicketDetailsAction) {
        when (action) {
            is TicketDetailsAction.Install -> installGateway(action.qrContent)
            TicketDetailsAction.Refresh -> TODO()
        }
    }
}
