package com.example.tenretni.ui.coordinators

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TopLevelViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TopLevelUiState())

    val uiState = _uiState.asStateFlow()
}