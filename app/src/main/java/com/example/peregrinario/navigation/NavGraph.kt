package com.example.peregrinario.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import com.example.peregrinario.ui.components.StandardBottomNavigation
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

@ExperimentalMaterial3Api
@Composable
fun NavGraph(preferencesViewModel: PreferencesViewModel, authViewModel: AuthViewModel, modifier: Modifier) {
    val navController = rememberNavController()

    val recentSearches = remember { mutableStateListOf<Travel>() }
    val isAnimationsEnabled by preferencesViewModel.displayAnimations.collectAsState()

    var isUserLogged by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.getUser { user ->
            isUserLogged = user != null
        }
    }

    val userLoggedState = rememberUpdatedState(isUserLogged)



    Scaffold(
        topBar = {
            if (userLoggedState.value) {
                TopAppBarWithMenu(
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onHelpClick = { navController.navigate(Screen.Help.route) },
                    onLogoutClick = {
                        authViewModel.logout()
                        isUserLogged = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (userLoggedState.value) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    StandardBottomNavigation(
                        items = bottomNavItems,
                        navController = navController
                    )
                }
            }
        },
        floatingActionButton = {
            if (isUserLogged) {
                Box(
                    Modifier.offset(y = 60.dp).size(60.dp)
                ){
                    Box(
                        Modifier
                            .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
                            .size(70.dp).shadow(5.dp, shape = CircleShape)
                    ){
                        FloatingActionButton(
                            modifier = Modifier.clip(CircleShape).size(60.dp)
                                .shadow(10.dp, shape = CircleShape),
                            containerColor = MaterialTheme.colorScheme.primary,
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                tint = Color.Black,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isUserLogged) Screen.Home.route else Screen.Login.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                if (isAnimationsEnabled) {
                    scaleIn(animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing))
                } else {
                    EnterTransition.None
                }
            },
            exitTransition = {
                if (isAnimationsEnabled) {
                    scaleOut(animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing))
                } else {
                    ExitTransition.None
                }
            },
            popEnterTransition = {
                if (isAnimationsEnabled) {
                    scaleIn(animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing))
                } else {
                    EnterTransition.None
                }
            },
            popExitTransition = {
                if (isAnimationsEnabled) {
                    scaleOut(animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing))
                } else {
                    ExitTransition.None
                }
            }
        )
        {
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


