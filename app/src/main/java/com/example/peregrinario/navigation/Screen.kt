package com.example.peregrinario.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
    object Help : Screen("help")
    object Details : Screen("details/{travelDestinationName}") {
        fun createRoute(destination: String) = "details/$destination"
    }
}