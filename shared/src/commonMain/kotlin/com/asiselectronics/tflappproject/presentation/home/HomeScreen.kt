package com.asiselectronics.tflappproject.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.asiselectronics.tflappproject.data.remote.TflApiService
import kotlinx.coroutines.launch



data class HubItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSchedules: () -> Unit,
    onNavigateToLiveVehicles: () -> Unit,
    onNavigateToStopsMap: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToStopSearch: () -> Unit,
    onNavigateToLines: () -> Unit
) {


    val hubItems = listOf(
        HubItem("Hatlar", Icons.Default.DirectionsBus, onNavigateToLines),
        HubItem("Ulaşım Araçları", Icons.Default.DirectionsBus, onNavigateToLiveVehicles),
        HubItem("Duraklar", Icons.Default.LocationOn, onNavigateToStopsMap),
        HubItem("Favoriler", Icons.Default.Star, onNavigateToFavorites)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text ( "BusWay" ) },
                navigationIcon = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Ayarları")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToAccount) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Hesabım")
                    }
                }
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Hat/Durak Ara") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null)},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToStopSearch() },
                enabled = false,
                singleLine = true
            )
            val scope = rememberCoroutineScope()
            val apiService = remember { TflApiService() }

            Button(onClick = {
                scope.launch {
                    val result = apiService.searchStopPoints("oxford circus")
                    result.fold(
                        onSuccess = { response ->
                            println("BAŞARILI: ${response.matches.size} durak bulundu")
                            response.matches.take(3).forEach {
                                println("  - ${it.name} (${it.id})")
                            }
                        },
                        onFailure = { error ->
                            println("HATA: ${error.message}")
                        }
                    )
                }
            }) {
                Text("API Test Et")
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 120.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(hubItems) { item ->
                    HubCard(item)
                }
            }
        }
    }

}

@Composable
private fun HubCard(item : HubItem){
    Card(
        onClick = item.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.padding(bottom = 8.dp),
                tint = MaterialTheme.colorScheme.primary

            )

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}









