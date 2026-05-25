package com.example.tenretni.ui.screens.main.GatewaysList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.R
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.GatewayRepository
import com.example.tenretni.models.Gateway
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GatewaysListViewModel : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _uiState = MutableStateFlow(GatewaysListUiState())
    val uiState = _uiState.asStateFlow()

    private var allGateways: List<Gateway> = emptyList()

    private var refreshTicketJob: Job? = null

    init {
        refreshGateways()
    }

    private fun refreshGateways() {
        refreshTicketJob?.cancel()
        refreshTicketJob = viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            while (isActive) {
                gatewayRepository.retrieveAll()
                    .catch { ex ->
                        ex.printStackTrace()
                        _uiState.update {
                            it.copy(
                                gatewayResult = AsyncResult.Error(R.string.error_message),
                                isRefreshing = false
                            )
                        }
                    }
                    .collect { gateways ->
                        allGateways = gateways
                        _uiState.update { currentState ->
                            currentState.copy(
                                gatewayResult = AsyncResult.Success(
                                    filterGateways(
                                        allGateways,
                                        currentState.searchText
                                    ),
                                ),
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
                gatewayResult = AsyncResult.Success(filterGateways(allGateways, newSearchText))
            )
        }
    }

    private fun filterGateways(gateways: List<Gateway>, searchText: String): List<Gateway> {
        return gateways.filter { gateway ->
            gateway.serialNumber.contains(searchText, ignoreCase = true)
        }
    }

    fun onAction(action: GatewaysListAction) {
        when (action) {
            is GatewaysListAction.OnSearch -> search(action.searchText)
            GatewaysListAction.RefreshGateways -> refreshGateways()
        }
    }

}