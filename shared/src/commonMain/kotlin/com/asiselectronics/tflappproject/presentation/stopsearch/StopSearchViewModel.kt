package com.asiselectronics.tflappproject.presentation.stopsearch

import androidx.compose.material3.SwipeToDismissBox
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.data.remote.TflApiService
import com.asiselectronics.tflappproject.data.remote.dto.StopPointMatchDto
import io.ktor.http.HttpMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StopSearchUiState(
    val query: String = "",
    val results: List<StopPointMatchDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)

class StopSearchViewModel(
    private val apiService: TflApiService = TflApiService()

): ViewModel() {
    private val _uiState = MutableStateFlow(StopSearchUiState())
    val uiState: StateFlow<StopSearchUiState> = _uiState.asStateFlow()

    private var searchJob : Job? = null

    fun onQueryChange(value : String){
        _uiState.value = _uiState.value.copy(query = value, errorMessage = null)

        searchJob?.cancel()

        if(value.isBlank()){
            _uiState.value = _uiState.value.copy(results = emptyList())
            return
        }
        searchJob = viewModelScope.launch {
            delay(400)
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result =apiService.searchStopPoints(value)
            result.fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        results = response.matches
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Arama Başarısız!"
                        )
                }
            )
        }

    }
}