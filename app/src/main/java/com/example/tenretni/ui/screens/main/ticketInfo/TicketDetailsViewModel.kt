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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch



class TicketDetailsViewModel : ViewModel() {
    private val customerRepository = CustomerRepository()

    private val _uiState = MutableStateFlow(TicketDetailsUiState())
    val uiState = _uiState.asStateFlow()

//    fun updateTicketStatus(status: String) {
//        _uiState.update { uiState ->
//            val currentTicket = uiState.ticket
//
//            uiState.copy(ticket = currentTicket.copy(status = status))
//        }
//    }

    private var refreshJob: Job? = null

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

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}
