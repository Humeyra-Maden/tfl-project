package com.asiselectronics.tflappproject.presentation.linedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.asiselectronics.tflappproject.data.remote.dto.RouteStationDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineDetailScreen(
    uiState: LineDetailUiState,
    lineName: String,
    onNavigateBack: () -> Unit,
    onViewTimeTable: (fromStopId: String) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lineName.replace("_", " ")) },
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

                uiState.stations.isEmpty() -> {
                    Text(
                        text = "Güzergah Bulunamadı",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    val first = uiState.stations.first()
                    val last = uiState.stations.last()
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Başlangıç Noktası: ${first.name}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Bitiş Noktası: ${last.name}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Button(
                            onClick = { onViewTimeTable(first.id) },
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        ) {
                            Text("Saatleri Gör")
                        }
                    }
                    Divider()
                    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                        items(uiState.stations, { station -> station.id }) { station ->
                            ListItem(
                                headlineContent = { Text(station.name) },
                                leadingContent = {
                                    Icon(Icons.Default.LocationOn, contentDescription = null)
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewLineDetailScreen() {
    LineDetailScreen(
        uiState = LineDetailUiState(
            stations = listOf(
                RouteStationDto("1", "Station 1"),
                RouteStationDto("2", "Station 2"),
                RouteStationDto("3", "Station 3")
            ),
            isLoading = false,
            errorMessage = null
        ),
        lineName = "1",
        onNavigateBack = {},
        onViewTimeTable = {}
    )
}