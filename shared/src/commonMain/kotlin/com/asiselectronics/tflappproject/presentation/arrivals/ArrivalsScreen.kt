package com.asiselectronics.tflappproject.presentation.arrivals


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivalsScreen(
    stopId: String,
    stopName: String,
    onNavigateBack: () -> Unit,
    viewModel: ArrivalsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(stopId) {
        viewModel.loadArrivals(stopId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stopName.replace("_", " ")) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                uiState.arrivals.isEmpty() -> {
                    Text(
                        text = "Şu an planlanmış varış yok",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                        items(uiState.arrivals) { arrival ->
                            val minutes = arrival.timeToStationSeconds / 60
                            ListItem(
                                headlineContent = { Text("${arrival.lineName} → ${arrival.destinationName}") },
                                supportingContent = {
                                    Text(if (minutes < 1) "Yaklaşıyor" else "$minutes dk")
                                },
                                leadingContent = {
                                    Icon(Icons.Default.DirectionsBus, contentDescription = null)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}