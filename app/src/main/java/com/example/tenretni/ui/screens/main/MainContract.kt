package com.example.tenretni.ui.screens.main

import androidx.annotation.StringRes
import com.example.tenretni.core.ui.navigation.TopBarOptions

sealed interface MainAction {
    data class UpdateTopBarOptions(val topBarOptions: TopBarOptions): MainAction
}

sealed interface MainEvent {
    data class ShowSnackbar(@field:StringRes val messageId: Int, @field:StringRes val actionId: Int? = null) : MainEvent
}
