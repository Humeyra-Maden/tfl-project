package com.asiselectronics.tflappproject.presentation.lines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.data.remote.TflApiService
import com.asiselectronics.tflappproject.data.remote.dto.LineMatcheDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LinesUiState(
    val query : String = "",
    val results : List<LineMatcheDto> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage : String? = null
)

class LinesViewModel(
    private val apiService: TflApiService = TflApiService()
) : ViewModel() {
    private val _uiState = MutableStateFlow(LinesUiState())
    val uiState : StateFlow<LinesUiState> = _uiState.asStateFlow()

    private var searchJob : Job? = null

    fun onQueryChange(value : String) {
        _uiState.value = _uiState.value.copy(query = value, errorMessage = null)

        searchJob?.cancel()

        if (value.isBlank()){
            _uiState.value = _uiState.value.copy(results = emptyList())
            return
        }

        searchJob = viewModelScope.launch {
            delay(400)
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = apiService.searchLines(value)
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
                        errorMessage = error.message ?: "Arama Başarısız"
                    )
                }
            )

        }
    }
}
