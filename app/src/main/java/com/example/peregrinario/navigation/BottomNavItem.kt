package com.example.peregrinario.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val contentDescription: String? = null,
    val icon: ImageVector? = null,
)
