package com.example.tenretni.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tenretni.core.ui.navigation.TopBarOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    //Events
    private val _events = Channel<MainEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ShowSnackbar -> {
                viewModelScope.launch {
                    _events.send(event)
                }
            }
        }
    }

    fun onAction(action: MainAction) {
        when(action) {
            is MainAction.UpdateTopBarOptions -> updateTopBarOptions(action.topBarOptions)
        }
    }

    private fun updateTopBarOptions(topBarOptions: TopBarOptions) {
        _uiState.update {
            it.copy(topBarOptions = topBarOptions)
        }
    }

}