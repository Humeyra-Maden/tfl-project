package com.asiselectronics.tflappproject.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asiselectronics.tflappproject.presentation.auth.login.LoginScreen
import com.asiselectronics.tflappproject.presentation.auth.register.RegisterScreen
import com.asiselectronics.tflappproject.presentation.home.HomeScreen
import com.asiselectronics.tflappproject.presentation.splash.SplashScreen
import com.asiselectronics.tflappproject.presentation.stopsearch.StopSearchScreen

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
                }
            )
        }

        composable(Screen.Settings.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                text = ("Ayarlar sayfası yakında"),
                    fontSize = 30.sp
            )
            }

        }
        composable(Screen.Account.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = ("Hesabım sayfası yakında"),
                    fontSize = 20.sp
                )
            }
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
                    // Şimdilik sadece geri dön, ileride durak detay ekranına gidecek
                    navController.popBackStack()
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
    }
}

