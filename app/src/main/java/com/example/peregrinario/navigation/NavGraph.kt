package com.example.peregrinario.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.BottomNavigationBar
import com.example.peregrinario.ui.screens.DetailsScreen
import com.example.peregrinario.ui.screens.FavoritesScreen
import com.example.peregrinario.ui.screens.HomeScreen

sealed class BottomBarScreen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : BottomBarScreen(
        route = "home",
        icon = { androidx.compose.material3.Icon(Icons.Default.Home, contentDescription = "Home") },
        label = "Home"
    )

    object Favorites : BottomBarScreen(
        route = "favorites",
        icon = { androidx.compose.material3.Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
        label = "Favoritos"
    )
}

@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomBarScreen.Home.route) {
                HomeScreen(
                    onTravelSelected = { travel ->
                        navController.navigate("details/${travel.destinationName}")
                    },
                    onSettingsClick = onSettingsClick,
                    onHelpClick = onHelpClick,
                    onFavoritesClick = onFavoritesClick,
                    onHomeClick = onHomeClick
                )
            }
            composable(BottomBarScreen.Favorites.route) {
                FavoritesScreen(
                    onTravelSelected = { travel ->
                        navController.navigate("details/${travel.destinationName}")
                    },
                    onFavoriteToggle = { travel ->
                        travel.isFavorite = !travel.isFavorite
                    },
                    onSettingsClick = onSettingsClick,
                    onHelpClick = onHelpClick,
                    onFavoritesClick = onFavoritesClick,
                    onHomeClick = onHomeClick
                )
            }
            composable("details/{travelDestinationName}") { backStackEntry ->
                val travelDestinationName = backStackEntry.arguments?.getString("travelDestinationName")
                val selectedTravel = travelList.first { it.destinationName == travelDestinationName }
                DetailsScreen(
                    selectedTravel,
                    onSettingsClick = onSettingsClick,
                    onHelpClick = onHelpClick,
                    onFavoritesClick = onFavoritesClick,
                    onHomeClick = onHomeClick
                )
            }
        }
    }
}


