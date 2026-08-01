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
    object StopSearch : Screen("stop_search")

    object Arrivals : Screen("arrivals/{stopId}/{stopName}") {
        fun createRoute(stopId: String, stopName: String): String {
            val safeName = stopName.replace(" ", "_")
            return "arrivals/$stopId/$safeName"
        }
    }

    object Lines : Screen("lines")

    object LineDetail : Screen("line_detail/{lineId}/{lineName}"){
        fun createRoute( lineId: String, lineName: String) : String{
            val safeName = lineName.replace(" ", "_")
            return "line_detail/$lineId/$safeName"
        }
    }
    object Timetable : Screen("timetable/{lineId}/{fromStopId}") {
        fun createRoute(lineId: String, fromStopId: String): String {
            return "timetable/$lineId/$fromStopId"
        }
    }
}
