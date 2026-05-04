package ca.qc.cstj.tenretni.core.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.example.tenretni.R


interface BottomNavItem : NavKey {
    @get:Composable
    val icon: ImageVector

    @get:Composable
    val title: String

}

data class TopBarOptions(
    val isTopBarVisible: Boolean = true,
    val isBackButtonVisible: Boolean = false,
    @param:StringRes val titleStringRes: Int = R.string.app_name,
    val titleArgs: List<Any> = emptyList()
) {
    companion object {
        val Defaults = TopBarOptions()
    }

    val title: String
        @Composable get() = if (titleArgs.isNotEmpty()) {
            stringResource(titleStringRes, *titleArgs.toTypedArray())
        } else {
            stringResource(titleStringRes)
        }

}

data class BottomBarOptions(
    val items: List<BottomNavItem> = listOf(),
    val isBottomBarVisible: Boolean = true
) {

    companion object {
        val Defaults = BottomBarOptions()
    }
}


interface Screen: NavKey {
    val topBarOptions: TopBarOptions
        get() = TopBarOptions.Defaults

    val bottomBarOptions: BottomBarOptions
        get() = BottomBarOptions.Defaults
}