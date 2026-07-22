package com.asiselectronics.tflappproject.navigation


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Settings : Screen("settings")
    object Account : Screen("account")
    object Schedules : Screen("schedules")
    object LiveVehicles : Screen("live_vehicles")
    object StopsMap : Screen("stops_map")
    object Favorites : Screen("favorites")
}
