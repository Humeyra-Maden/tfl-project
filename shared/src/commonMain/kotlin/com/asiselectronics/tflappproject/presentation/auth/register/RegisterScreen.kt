package com.asiselectronics.tflappproject.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()

) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registerSuccess){
        if(uiState.registerSuccess){
            onRegisterSuccess()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )   {

        Text(
            text = "Kayıt Ol",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("E-posta")},
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = {Text("Şifre")},
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = { Text("Şifre Tekrar") },
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.register()},
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            enabled = !uiState.isLoading

        ) {
            if(uiState.isLoading){
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Kayıt Ol")
            }
        }
        TextButton(onClick = onNavigateToLogin) {
            Text("Hesabın zaten var mı? Giriş Yap.")
        }

    }




}
