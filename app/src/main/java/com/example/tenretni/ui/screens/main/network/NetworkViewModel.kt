package com.example.tenretni.ui.screens.main.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.core.Constants
import com.example.tenretni.data.repositories.NetworkRepository
import com.example.tenretni.models.Node
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NetworkViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NetworkUiState())
    val uiState = _uiState.asStateFlow()

    private val networkRepository = NetworkRepository()
    private var refreshJob: Job? = null

    init {
        loadNetwork()
    }

    private fun loadNetwork() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                networkRepository.getNetwork()
                    .catch { ex ->
                        ex.printStackTrace()
                    }
                    .collect { network ->
                        _uiState.update {
                            it.copy(network = network)
                        }
                    }
                delay(Constants.RefreshDelay.NETWORK_REFRESH_DELAY)
            }
        }
    }

    fun selectNode(node: Node) {
        _uiState.update {
            it.copy(selectedNode = node)
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}
