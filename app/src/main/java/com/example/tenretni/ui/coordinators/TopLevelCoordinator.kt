package com.example.tenretni.ui.coordinators

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.tenretni.ui.navigation.Route
import com.example.tenretni.ui.screens.main.MainScreen
import com.example.tenretni.ui.screens.main.ticketsList.TicketsScreen
import com.example.tenretni.ui.screens.main.title.TitleScreen

@Composable
fun TopLevelCoordinator(
    viewModel: TopLevelViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val backStack = uiState.backStack

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        transitionSpec = { slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut() },
        popTransitionSpec = { slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut() },
        predictivePopTransitionSpec = { slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut() },
        entryProvider = entryProvider {
            entry<Route.toMainScreen> {
                MainScreen()
            }
            entry<Route.ToTicketsScreen> {
                TicketsScreen()
            }
            entry<Route.ToTitleScreen> {
                TitleScreen(
                    navigateToMain = { backStack.add(Route.toMainScreen) }
                )

            }
        }
    )
}