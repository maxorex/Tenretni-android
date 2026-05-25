package com.example.tenretni.ui.screens.main.ticketInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import com.example.tenretni.R
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.data.repositories.GatewayRepository
import kotlinx.coroutines.channels.Channel
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
                    .catch { _ ->
                        _uiState.update { it.copy(customerResult = AsyncResult.Error(R.string.error_message)) }
                    }
                    .collect { customers ->
                        val customer = customers.find { it.href == customerHref }
                        if (customer != null) {
                            _uiState.update { it.copy(customerResult = AsyncResult.Success(customer)) }
                        } else {
                            _uiState.update { it.copy(customerResult = AsyncResult.Error(R.string.error_message)) }
                        }
                    }
                delay(Constants.RefreshDelay.CUSTOMER_GATEWAY_REFRESH_DELAY)
            }
        }
    }


    private fun installGateway(customerHref: String?, rawQr: String?) {
        viewModelScope.launch {
            if (rawQr == null) {
                _events.send(TicketDetailsEvent.OnError(R.string.qr_code_error))
                return@launch
            }

            if(customerHref.isNullOrBlank()) {
                viewModelScope.launch {
                    _events.send(TicketDetailsEvent.OnError(R.string.error_while_creating_installing_the_new_gateway))
                    return@launch
                }
            }


            gatewayRepository.installCustomerGateway(customerHref, rawQr).catch {
                _events.send(TicketDetailsEvent.OnError(R.string.error_while_creating_installing_the_new_gateway))
            }.collect {
                Log.d("INSTALL", it)
            }
        }
    }

    fun updateTicketStatus(status: String, ticketId: String) {
        viewModelScope.launch {
            ticketRepository.updateStatus(ticketId, status)
                .flowOn(Dispatchers.IO)
                .catch { _ ->
                    _uiState.update { it.copy(ticketResult = AsyncResult.Error(R.string.error_message)) }
                }
                .collect { updatedTicket ->
                    _uiState.update { it.copy(ticketResult = AsyncResult.Success(updatedTicket)) }
                }
        }
    }

    fun onAction(action: TicketDetailsAction) {
        when (action) {
            is TicketDetailsAction.Install -> installGateway(action.customerHref, action.qrContent)
            is TicketDetailsAction.Update -> updateTicketStatus(action.status, action.ticketId)
            TicketDetailsAction.Refresh -> {
                // Manual refresh logic could go here if needed
            }
        }
    }
}
