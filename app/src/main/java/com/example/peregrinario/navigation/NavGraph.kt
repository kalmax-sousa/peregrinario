package com.example.peregrinario.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import com.example.peregrinario.ui.screens.DetailsScreen
import com.example.peregrinario.ui.screens.FavoritesScreen
import com.example.peregrinario.ui.screens.HelpScreen
import com.example.peregrinario.ui.screens.HomeScreen
import com.example.peregrinario.ui.screens.SettingsScreen
import com.example.peregrinario.viewmodel.PreferencesViewModel

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

    object Help : BottomBarScreen(
        route = "help",
        icon = { androidx.compose.material3.Icon(Icons.Default.Favorite, contentDescription = "Help") },
        label = "Ajuda"
    )

    object Settings : BottomBarScreen(
        route = "settings",
        icon = { androidx.compose.material3.Icon(Icons.Default.Favorite, contentDescription = "Settings") },
        label = "Configurações"
    )
}

@ExperimentalMaterial3Api
@Composable
fun NavGraph(preferencesViewModel: PreferencesViewModel) {
    val navController = rememberNavController()

    val recentSearches = remember { mutableStateListOf<Travel>() }

    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                onSettingsClick = {
                    navController.navigate(BottomBarScreen.Settings.route)
                },
                onHelpClick = {
                    navController.navigate(BottomBarScreen.Help.route)
                },
                onFavoritesClick = {
                    navController.navigate(BottomBarScreen.Favorites.route)
                },
                onHomeClick = {
                    navController.navigate(BottomBarScreen.Home.route)
                }
            )
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
                    recentSearches
                )
            }
            composable(BottomBarScreen.Favorites.route) {
                FavoritesScreen(
                    onTravelSelected = { travel ->
                        navController.navigate("details/${travel.destinationName}")
                    },
                    onFavoriteToggle = { travel ->
                        travel.isFavorite.value = !travel.isFavorite.value
                    }
                )
            }
            composable(BottomBarScreen.Settings.route) {
                SettingsScreen(preferencesViewModel)
            }

            composable(BottomBarScreen.Help.route) {
                HelpScreen(preferencesViewModel)
            }


            composable("details/{travelDestinationName}") { backStackEntry ->
                val travelDestinationName = backStackEntry.arguments?.getString("travelDestinationName")
                val selectedTravel = travelList.first { it.destinationName == travelDestinationName }
                DetailsScreen(
                    selectedTravel
                )
            }
        }
    }
}


