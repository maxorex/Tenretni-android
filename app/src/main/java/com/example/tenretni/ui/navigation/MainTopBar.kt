package com.example.tenretni.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.tenretni.core.ui.navigation.TopBarOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    topBarOptions: TopBarOptions,
    onNavigateBack: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(text = topBarOptions.title)
        },
        navigationIcon = {
            if (onNavigateBack != null && topBarOptions.isBackButtonVisible) {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, null
                    )
                }
        }
    })
}