package com.example.tenretni.ui.screens.main.GatewaysList

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tenretni.core.AsyncResult
import com.example.tenretni.core.ui.components.ErrorMessage
import com.example.tenretni.core.ui.components.LoadingAnimation
import com.example.tenretni.models.Gateway
import com.example.tenretni.ui.components.GatewayListCard
import com.example.tenretni.ui.screens.main.ticketsList.SearchBar
import com.example.tenretni.ui.screens.main.ticketsList.TicketsListAction
import com.example.tenretni.ui.screens.main.ticketsList.TicketsListContent

@Composable
fun GatewaysListScreen(
    viewModel: GatewaysListViewModel = viewModel(),
    toGatewayDetailScreen: (Gateway) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitMode(
            uiState = uiState,
            onAction = viewModel::onAction,
            toGatewayDetailScreen = toGatewayDetailScreen
        )
    } else {
        LandscapeMode(
            uiState = uiState,
            onAction = viewModel::onAction,
            toGatewayDetailScreen = toGatewayDetailScreen
        )
    }
}

@Composable
fun PortraitMode(
    uiState: GatewaysListUiState,
    onAction: (GatewaysListAction) -> Unit,
    toGatewayDetailScreen: (Gateway) -> Unit
) {
    Column() {
        SearchBar(
            searchText = uiState.searchText,
            onSearch = { searchText -> onAction(GatewaysListAction.OnSearch(searchText)) }
        )
        GatewaysListContent(
            uiState = uiState,
            onAction = onAction,
            toGatewayDetailScreen = toGatewayDetailScreen,
        )
    }
}

@Composable
fun LandscapeMode(
    uiState: GatewaysListUiState,
    onAction: (GatewaysListAction) -> Unit,
    toGatewayDetailScreen: (Gateway) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            SearchBar(
                searchText = uiState.searchText,
                onSearch = { searchText -> onAction(GatewaysListAction.OnSearch(searchText)) }
            )
        }
        Column(modifier = Modifier.weight(2f)) {
            GatewaysListContent(
                uiState = uiState,
                onAction = onAction,
                toGatewayDetailScreen = toGatewayDetailScreen,
            )
        }
    }

}

@Composable
fun SearchBar(
    searchText: String,
    onSearch: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp, color = LocalTextStyle.current.color, shape = RoundedCornerShape(8.dp)
            ),
        value = searchText,
        onValueChange = { value -> onSearch(value) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        placeholder = {
            Text("Search for a ticket number", fontWeight = FontWeight.SemiBold)
        }
    )
}

@Composable
fun GatewaysListContent(
    uiState: GatewaysListUiState,
    onAction: (GatewaysListAction) -> Unit,
    toGatewayDetailScreen: (Gateway) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = uiState.isRefreshing) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary
            )
        }

        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onAction(GatewaysListAction.RefreshGateways) }
        ) {
            when (uiState.gatewayResult) {
                is AsyncResult.Error -> {
                    ErrorMessage(
                        errorMessageId = uiState.gatewayResult.messageResId,
                        onTryAgainClick = { onAction(GatewaysListAction.RefreshGateways) }
                    )
                }

                AsyncResult.Loading -> LoadingAnimation()
                is AsyncResult.Success -> {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            items(uiState.gatewayResult.data) { gateway ->
                                GatewayListCard(
                                    gateway = gateway,
                                    onClick = {
                                        toGatewayDetailScreen(gateway)
                                    }
                                )
                            }
                        }
                }
            }
        }
    }
}