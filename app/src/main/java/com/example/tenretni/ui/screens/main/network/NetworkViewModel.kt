package com.example.tenretni.ui.screens.main.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenretni.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tenretni.models.Node
import com.example.tenretni.models.Connection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NetworkViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NetworkUiState())


    val uiState = _uiState.asStateFlow()

    private val networkRepository = NetworkRepository()

    init {
        loadNetwork()
    }

    private fun loadNetwork() {
        networkRepository.getNetwork().onEach { network ->
            _uiState.update {
                it.copy(network = network)
            }
        }.launchIn(viewModelScope)
    }

    fun selectNode(node: Node) {
        _uiState.update {
            it.copy(selectedNode = node)
        }
    }

}