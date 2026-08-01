package com.asiselectronics.tflappproject.navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.asiselectronics.tflappproject.presentation.account.AccountScreen
import com.asiselectronics.tflappproject.presentation.arrivals.ArrivalsScreen
import com.asiselectronics.tflappproject.presentation.auth.login.LoginScreen
import com.asiselectronics.tflappproject.presentation.auth.register.RegisterScreen
import com.asiselectronics.tflappproject.presentation.home.HomeScreen
import com.asiselectronics.tflappproject.presentation.linedetail.LineDetailRoute
import com.asiselectronics.tflappproject.presentation.linedetail.LineDetailScreen
import com.asiselectronics.tflappproject.presentation.linedetail.LineDetailViewModel
import com.asiselectronics.tflappproject.presentation.splash.SplashScreen
import com.asiselectronics.tflappproject.presentation.stopsearch.StopSearchScreen
import org.jetbrains.compose.resources.getString
import com.asiselectronics.tflappproject.presentation.lines.LinesScreen
import com.asiselectronics.tflappproject.presentation.settings.SettingsScreen
import com.asiselectronics.tflappproject.presentation.timetable.TimetableScreen
import org.jetbrains.compose.resources.stringResource
import tflappproject.shared.generated.resources.Res
import tflappproject.shared.generated.resources.test

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )

        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToAccount = {
                    navController.navigate(Screen.Account.route)
                },
                onNavigateToSchedules = {
                    navController.navigate(Screen.Schedules.route)
                },
                onNavigateToLiveVehicles = {
                    navController.navigate(Screen.LiveVehicles.route)
                },
                onNavigateToStopsMap = {
                    navController.navigate(Screen.StopsMap.route)
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                onNavigateToStopSearch = {
                    navController.navigate(Screen.StopSearch.route)
                },
                onNavigateToLines = {
                    navController.navigate(Screen.Lines.route)
                }
            )
        }

        composable(Screen.Settings.route){
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )

        }
        composable(Screen.Account.route){
            AccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onLoggedOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) // tüm back stack'i temizle, geri tuşuyla Home'a dönülmesin
                    }
                }
            )
        }
        composable(Screen.Schedules.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = ("Hat saatleri sayfası yakında"),
                    fontSize = 20.sp
                )
            }
        }

        composable(Screen.LiveVehicles.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = ("Canlı Araçlar sayfası yakında"),
                    fontSize = 20.sp
                )
            }
        }

        composable(Screen.StopSearch.route){
            StopSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onStopSelected = { stop ->
                    navController.navigate(Screen.Arrivals.createRoute(stop.id, stop.name))
                }
            )
        }
        composable(Screen.Favorites.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = ("Favoriler sayfası yakında"),
                    fontSize = 20.sp
                )
            }
        }


        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Arrivals.route,
            arguments = listOf(
                navArgument("stopId") { type = NavType.StringType },
                navArgument("stopName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val stopId = backStackEntry.arguments?.read{getString("stopId")} ?: ""
            val stopName = backStackEntry.arguments?.read{getString("stopName")} ?: ""
            ArrivalsScreen(
                stopId = stopId,
                stopName = stopName,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.StopsMap.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = ("Duraklar haritası yakında gelecek"),
                    fontSize = 20.sp
                )
            }
        }

        composable(Screen.Lines.route){
            LinesScreen(
                onNavigateBack = { navController.popBackStack() },
                onLineSelected = { line ->
                    navController.navigate(Screen.LineDetail.createRoute(line.lineId, line.lineName))
                }
            )
        }
        composable(
            route = Screen.LineDetail.route,
            arguments = listOf(
                navArgument("lineId") { type = NavType.StringType },
                navArgument("lineName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lineId = backStackEntry.arguments?.read { getString("lineId") } ?: ""
            val lineName = backStackEntry.arguments?.read { getString("lineName") } ?: ""
            val viewModel = viewModel<LineDetailViewModel>()
            LineDetailRoute(
                viewModel= viewModel,
                lineId = lineId,
                lineName = lineName,
                onNavigateBack = { navController.popBackStack() },
                onViewTimeTable = { fromStopId ->
                    navController.navigate(Screen.Timetable.createRoute(lineId, fromStopId))
                }
            )
        }
        composable(
            route = Screen.Timetable.route,
            arguments = listOf(
                navArgument("lineId") { type = NavType.StringType },
                navArgument("fromStopId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lineId = backStackEntry.arguments?.read { getString("lineId") } ?: ""
            val fromStopId = backStackEntry.arguments?.read { getString("fromStopId") } ?: ""
            TimetableScreen(
                lineId = lineId,
                fromStopId = fromStopId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

