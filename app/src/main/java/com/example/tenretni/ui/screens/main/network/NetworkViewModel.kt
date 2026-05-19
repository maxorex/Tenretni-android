package com.example.tenretni.ui.screens.main.network

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tenretni.models.Node
import com.example.tenretni.models.Connection


class NetworkViewModel : ViewModel() {
    //    private val _uiState = MutableStateFlow(NetworkUiState())
    private val _uiState = MutableStateFlow(
        NetworkUiState(
            nodes = listOf(
                Node(
                    name = "Eplil",
                    connection = Connection(
                        status = "Offline",
                        download = 0f,
                        ip = "2.2.2.2",
                        ping = 0f,
                        signal = 0f,
                        upload = 0f
                    )
                ),
                Node(
                    name = "Lukryx",
                    connection = Connection(
                        status = "Online",
                        download = 0f,
                        ip = "3.3.3.3",
                        ping = 0f,
                        signal = 0f,
                        upload = 0f
                    )
                )
            )
        )
    )


    val uiState = _uiState.asStateFlow()


}