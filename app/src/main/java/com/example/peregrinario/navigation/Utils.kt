package com.example.peregrinario.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

val bottomNavItems = listOf<BottomNavItem>(
    BottomNavItem(
        route = "home",
        contentDescription = "Home",
        icon = Icons.Default.Home,
    ),
    BottomNavItem(
        route = "home",
        contentDescription = "Search",
        icon = Icons.Default.Search,
    ),
    BottomNavItem(
        route = "-",
    ),
    BottomNavItem(
        route = "favorites",
        contentDescription = "Favorites",
        icon = Icons.Default.Favorite,
    ),
    BottomNavItem(
        route = "favorites",
        contentDescription = "Profile",
        icon = Icons.Default.Person,
    )
)