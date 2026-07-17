package com.asiselectronics.tflappproject.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.asiselectronics.tflappproject.auth.isUserLoggedIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import tflappproject.shared.generated.resources.Res
import tflappproject.shared.generated.resources.bus_way_logo

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        if (isUserLoggedIn()) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B2B5C)),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(Res.drawable.bus_way_logo),
            contentDescription = "London BusWay Logo",
            modifier = Modifier.size(400.dp)

        )
    }
}