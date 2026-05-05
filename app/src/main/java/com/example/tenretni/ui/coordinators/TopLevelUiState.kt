package com.example.tenretni.ui.coordinators

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.tenretni.ui.navigation.Route

data class TopLevelUiState (
    val backStack : SnapshotStateList<Route> = mutableStateListOf<Route>(Route.ToTitleScreen)
)