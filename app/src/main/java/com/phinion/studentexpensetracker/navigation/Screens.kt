package com.phinion.studentexpensetracker.navigation

sealed class Screens(val route: String) {
    object HomeScreen: Screens(route = "home_screen")
    object AddUserScreen: Screens(route = "add_user_screen")
    object AnalyticsScreen: Screens(route = "analytics_screen")
    object Profile: Screens(route = "profile_screen")
    object WelcomeScreen: Screens(route = "welcome_screen")
    object SplashScreen: Screens(route = "splash_screen")
    object UpdateScreen: Screens(route = "update_screen/{transactionId}")
}