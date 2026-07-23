package com.asiselectronics.tflappproject.presentation.stopsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asiselectronics.tflappproject.data.remote.dto.StopPointMatchDto
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopSearchScreen(
    onNavigateBack: () -> Unit,
    onStopSelected: (StopPointMatchDto) -> Unit,
    viewModel: StopSearchViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }

    Scaffold (
     topBar = {
         TopAppBar(
             title = { Text("Durak Ara") },
             navigationIcon = {
                 IconButton(onClick = onNavigateBack) {
                     Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                 }
             }
         )
     }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)

        ) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Örn: Oxford Circus") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true
            )

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                        )
                }
                uiState.results.isEmpty() && uiState.query.isNotEmpty() -> {
                    Text(
                        text = "Sonuç Bulunamadı!",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 8.dp)
                    ) {
                        items(uiState.results) { stop ->
                            ListItem(
                                headlineContent = { Text(stop.name) },
                                leadingContent = {
                                    Icon(Icons.Default.LocationOn, contentDescription = null)
                                },
                                modifier = Modifier.clickable { onStopSelected(stop) }
                            )
                        }
                    }
                }
            }
        }
    }

}