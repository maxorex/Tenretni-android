package com.example.tenretni.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.example.tenretni.core.ui.navigation.BottomBarOptions
import com.example.tenretni.core.ui.navigation.TopLevelBackStack

@Composable
fun MainNavigationBar(
    bottomBarOptions: BottomBarOptions,
    topLevelBackStack: TopLevelBackStack<NavKey>
) {

    NavigationBar {
        bottomBarOptions.items.forEach { item ->
            val selected = topLevelBackStack.topLevelKey == item
            NavigationBarItem(
                selected = selected,
                onClick = {
                    topLevelBackStack.switchTopLevel(item)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = item.icon, contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
            )
        }
    }


}