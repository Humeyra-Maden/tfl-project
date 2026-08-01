package com.asiselectronics.tflappproject.presentation.linedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asiselectronics.tflappproject.data.remote.TflApiService
import com.asiselectronics.tflappproject.data.remote.dto.RouteStationDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LineDetailUiState(
    val stations : List<RouteStationDto> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage : String? = null
)

class LineDetailViewModel(
    private val apiService: TflApiService = TflApiService()
) : ViewModel() {
    private val _uiState = MutableStateFlow(LineDetailUiState())
    val uiState : StateFlow<LineDetailUiState> = _uiState.asStateFlow()


    fun loadRoute(lineId : String){
        viewModelScope.launch{
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = apiService.getRouteSequence(lineId)
            result.fold(
                onSuccess = { response ->
                    println("DEBUG: ${response.stations.size} durak geldi")
                    _uiState.value = _uiState.value.copy(isLoading = false, stations = response.stations)
                },
                onFailure= {error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Güzergah Alınamadı")
                }
            )
        }
    }
}