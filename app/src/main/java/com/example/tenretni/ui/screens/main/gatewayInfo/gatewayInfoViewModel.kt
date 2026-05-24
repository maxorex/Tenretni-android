package com.example.tenretni.ui.screens.main.gatewayInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.R
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.GatewayRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GatewayInfoViewModel: ViewModel() {
    private val gatewayRepository = GatewayRepository()
    private val _uiState = MutableStateFlow(GatewayInfoUiState())
    val uiState = _uiState.asStateFlow()

    private var refreshGatewayJob: Job? = null

    fun refreshGateway(serialNumber: String) {
        refreshGatewayJob?.cancel()
        refreshGatewayJob = viewModelScope.launch {
            while (isActive) {
                val isAlreadyLoaded = _uiState.value.gatewayResult is AsyncResult.Success

                _uiState.update {
                    if (isAlreadyLoaded) {
                        it.copy(isRefreshing = true)
                    } else {
                        it.copy(gatewayResult = AsyncResult.Loading)
                    }
                }

                gatewayRepository.retrieveOne(serialNumber).catch {
                    _uiState.update {
                        it.copy(
                            gatewayResult = AsyncResult.Error(R.string.error_message),
                            isRefreshing = false
                        )
                    }
                }.collect { gateway ->
                    _uiState.update {
                        if (gateway != null) {
                            it.copy(
                                gatewayResult = AsyncResult.Success(gateway),
                                isRefreshing = false
                            )
                        } else {
                            it.copy(
                                gatewayResult = AsyncResult.Error(R.string.error_message),
                                isRefreshing = false
                            )
                        }
                    }
                }
                delay(Constants.RefreshDelay.GATEWAY_REFRESH_DELAY)
            }
        }
    }
}
