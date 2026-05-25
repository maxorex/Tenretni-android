package com.example.tenretni.ui.screens.main.ticketInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import com.example.tenretni.data.repositories.GatewayRepository
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.models.Gateway
import kotlinx.coroutines.Job

import kotlinx.coroutines.delay
import com.example.tenretni.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

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
                    .catch { ex ->
                        _uiState.update { it.copy(customerResult = AsyncResult.Error(ex.hashCode())) }
                    }
                    .collect { customers ->
                        val customer = customers.find { it.href == customerHref }
                        if (customer != null) {
                            _uiState.update { it.copy(customerResult = AsyncResult.Success(customer)) }
                        } else {
                            _uiState.update { it.copy(customerResult = AsyncResult.Error(customer.hashCode())) }
                        }
                    }
                delay(Constants.RefreshDelay.CUSTOMER_GATEWAY_REFRESH_DELAY)
            }
        }
    }

    fun refreshGateways(customerHref: String) {
        viewModelScope.launch {
            gatewayRepository.retrieveCustomerGateways(customerHref).catch {
                _uiState.update {
                    it.copy(customerGateways = AsyncResult.Error(R.string.error_while_refreshing_gateways))
                }
            }.collect { gateways ->
                _uiState.update {
                    it.copy(customerGateways = AsyncResult.Success(gateways))
                }
            }
        }
    }


    private fun installGateway(customerHref: String?, rawQr: String?) {
        viewModelScope.launch {
            if (rawQr == null) {
                _events.send(TicketDetailsEvent.OnError(R.string.qr_code_error))
                return@launch
            }

            if (customerHref.isNullOrBlank()) {
                viewModelScope.launch {
                    _events.send(TicketDetailsEvent.OnError(R.string.error_while_creating_installing_the_new_gateway))
                    return@launch
                }
            }

            gatewayRepository.installCustomerGateway(customerHref, rawQr).catch {
                _events.send(TicketDetailsEvent.OnError(R.string.error_while_creating_installing_the_new_gateway))
                AsyncResult.Error(R.string.error_while_creating_installing_the_new_gateway)
            }.collect {
                val gatewayFromString = Json.decodeFromString<Gateway>(it)
                AsyncResult.Success(gatewayFromString)
                _events.send(TicketDetailsEvent.OnSuccess(R.string.gateway_installed_successfully))
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
                            _uiState.update {
                                it.copy(
                                    ticketResult = AsyncResult.Success(
                                        updatedTicket
                                    )
                                )
                            }
                        }
                }
            }


            fun onAction(action: TicketDetailsAction) {
                when (action) {
                    is TicketDetailsAction.Update -> updateTicketStatus(
                        action.status,
                        action.ticketId
                    )

                    is TicketDetailsAction.Install -> installGateway(
                        action.customerHref,
                        action.qrContent
                    )

                    is TicketDetailsAction.Refresh -> refreshGateways(action.customerHref)
                }
            }

}

