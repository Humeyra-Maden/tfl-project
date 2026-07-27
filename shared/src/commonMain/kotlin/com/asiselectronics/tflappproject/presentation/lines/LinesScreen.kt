package com.asiselectronics.tflappproject.presentation.lines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChipDefaults.contentPadding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asiselectronics.tflappproject.data.remote.dto.LineMatcheDto
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinesScreen(
    onNavigateBack: () -> Unit,
    onLineSelected: (LineMatcheDto) -> Unit,
    viewModel: LinesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold  (
        topBar = {
            TopAppBar(
                title = { Text("Hatlar") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri" )
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
                placeholder = { Text("Örn: Victoria,24") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            when {
                uiState.isLoading ->{
                    CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                uiState.results.isEmpty() && uiState.query.isNotBlank() -> {
                    Text (
                        text =  "Sonuç Bulunamadı" ,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else -> {
                    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                        items(uiState.results) { line ->
                            ListItem(
                                headlineContent = { Text(line.lineName) },
                                supportingContent = { Text(line.mode) },
                                leadingContent = {
                                    Icon(Icons.Default.DirectionsBus, contentDescription = null)
                                },
                                modifier = Modifier.clickable { onLineSelected(line) }
                            )
                        }
                    }
                }
            }
        }

    }
}
