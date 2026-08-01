package com.asiselectronics.tflappproject.presentation.timetable


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.data.remote.TflApiService
import com.asiselectronics.tflappproject.data.remote.dto.KnownJourneyDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TimetableUiState(
    val scheduleName: String = "",
    val journeys: List<KnownJourneyDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class TimetableViewModel(
    private val apiService: TflApiService = TflApiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimetableUiState())
    val uiState: StateFlow<TimetableUiState> = _uiState.asStateFlow()

    fun loadTimetable(lineId: String, fromStopId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = apiService.getTimetable(lineId, fromStopId)
            result.fold(
                onSuccess = { response ->
                    val firstSchedule = response.timetable
                        ?.routes?.firstOrNull()
                        ?.schedules?.firstOrNull()

                    if (firstSchedule != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            scheduleName = firstSchedule.name,
                            journeys = firstSchedule.knownJourneys
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Tarife bulunamadı"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Tarife alınamadı"
                    )
                }
            )
        }
    }
}