package com.example.tenretni.ui.screens.main.ticketsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.TicketRepository
import com.example.tenretni.models.Ticket
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TicketsViewModel : ViewModel() {

    private val ticketRepository = TicketRepository()

    private val _uiState = MutableStateFlow(TicketsUiState())
    val uiState = _uiState.asStateFlow()

    private var allTickets: List<Ticket> = emptyList()
    private var refreshTicketJob: Job? = null

    init {
        refreshTicket()
    }

    private fun refreshTicket() {
        refreshTicketJob?.cancel()
        refreshTicketJob = viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            while (isActive) {
                ticketRepository.retrieveAll()
                    .catch { ex ->
                        _uiState.update { it.copy(ticketResult = AsyncResult.Error(ex.hashCode()), isRefreshing = false) }
                    }
                    .collect { tickets ->
                        allTickets = tickets
                        _uiState.update { currentState ->
                            currentState.copy(
                                ticketResult = AsyncResult.Success(filterTickets(allTickets, currentState.searchText)),
                                isRefreshing = false
                            )
                        }
                    }
                delay(Constants.RefreshDelay.TICKET_REFRESH_DELAY)
            }
        }
    }

    private fun search(newSearchText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchText = newSearchText,
                ticketResult = AsyncResult.Success(filterTickets(allTickets, newSearchText))
            )
        }
    }

    private fun filterTickets(tickets: List<Ticket>, searchText: String): List<Ticket> {
        return tickets.filter { ticket ->
            ticket.ticketNumber.contains(searchText, ignoreCase = true)
        }
    }

    fun onAction(action: TicketsListAction) {
        when (action) {
            TicketsListAction.RefreshTicket -> refreshTicket()
            is TicketsListAction.OnSearch -> search(action.searchText)
            is TicketsListAction.OpenTicketDetail -> { /* Handled by UI navigation */ }
        }
    }

}
