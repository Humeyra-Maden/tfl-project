package com.asiselectronics.tflappproject.presentation.arrivals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.data.remote.TflApiService
import com.asiselectronics.tflappproject.data.remote.dto.ArrivalDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ArrivalsUiState(
    val arrivals: List<ArrivalDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ArrivalsViewModel(
    private val apiService: TflApiService = TflApiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArrivalsUiState())
    val uiState: StateFlow<ArrivalsUiState> = _uiState.asStateFlow()

    fun loadArrivals(naptanId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = apiService.getArrivals(naptanId)
            result.fold(
                onSuccess = { arrivals ->
                    _uiState.value = _uiState.value.copy(isLoading = false, arrivals = arrivals)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Varış bilgisi alınamadı"
                    )
                }
            )
        }
    }
}