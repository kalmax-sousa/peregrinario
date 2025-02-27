package com.example.peregrinario.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.peregrinario.navigation.BottomNavItem

@Composable
fun StandardBottomNavigation(
    navController: NavController,
    items: List<BottomNavItem>,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = Modifier.fillMaxWidth().shadow(elevation = (-5).dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
                enabled = item.icon != null,
                icon = {
                    if (item.icon != null /*&& item.selectedIcon != null*/) {
//                        var icons = painterResource(id = item.icon)
                        /*if (selected) {
                            icons = painterResource(id = item.selectedIcon)
                        }*/
                        Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                    }
                }
            )
        }
    }
}