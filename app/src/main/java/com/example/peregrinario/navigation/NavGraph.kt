package com.example.peregrinario.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import com.example.peregrinario.ui.screens.DetailsScreen
import com.example.peregrinario.ui.screens.FavoritesScreen
import com.example.peregrinario.ui.screens.ForgotPasswordScreen
import com.example.peregrinario.ui.screens.HelpScreen
import com.example.peregrinario.ui.screens.HomeScreen
import com.example.peregrinario.ui.screens.LoginScreen
import com.example.peregrinario.ui.screens.RegisterScreen
import com.example.peregrinario.ui.screens.SettingsScreen
import com.example.peregrinario.viewmodel.AuthViewModel
import com.example.peregrinario.viewmodel.PreferencesViewModel
/*
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
*/
@ExperimentalMaterial3Api
@Composable
fun NavGraph(preferencesViewModel: PreferencesViewModel, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    val recentSearches = remember { mutableStateListOf<Travel>() }
    var isUserLogged by remember { mutableStateOf(false) }

    LaunchedEffect(navController.currentBackStackEntry) {
        authViewModel.getUser { user ->
            isUserLogged = user != null
        }
    }

    Scaffold(
        topBar = {
            if (isUserLogged) {
                TopAppBarWithMenu(
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onHelpClick = { navController.navigate(Screen.Help.route) },
                    onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                    onHomeClick = { navController.navigate(Screen.Home.route) },
                    onLogoutClick = {
                        authViewModel.logout()
                        isUserLogged = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true } // Evita voltar para telas protegidas
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isUserLogged) Screen.Home.route else Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = authViewModel,
                    navController = navController
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    viewModel = authViewModel,
                    navController = navController
                )
            }

            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(
                    viewModel = authViewModel,
                    navController = navController
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onTravelSelected = { travel ->
                        navController.navigate("details/${travel.destinationName}")
                    },
                    recentSearches
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onTravelSelected = { travel ->
                        navController.navigate("details/${travel.destinationName}")
                    },
                    onFavoriteToggle = { travel ->
                        travel.isFavorite.value = !travel.isFavorite.value
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(preferencesViewModel)
            }

            composable(Screen.Help.route) {
                HelpScreen(preferencesViewModel)
            }

            composable(Screen.Details.route) { backStackEntry ->
                val travelDestinationName = backStackEntry.arguments?.getString("travelDestinationName")
                val selectedTravel = travelList.first { it.destinationName == travelDestinationName }
                DetailsScreen(selectedTravel)
            }
        }
    }
}


