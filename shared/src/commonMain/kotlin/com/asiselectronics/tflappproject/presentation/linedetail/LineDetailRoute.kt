package com.asiselectronics.tflappproject.presentation.linedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.asiselectronics.tflappproject.navigation.Screen

@Composable
fun LineDetailRoute(
    viewModel: LineDetailViewModel,
    lineId: String,
    lineName: String,
    onNavigateBack: () -> Unit,
    onViewTimeTable: (fromStopId: String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(lineId) {
        viewModel.loadRoute(lineId)
    }

    LineDetailScreen(
        uiState = uiState,
        lineName = lineName,
        onNavigateBack = onNavigateBack,
        onViewTimeTable = onViewTimeTable
    )

}