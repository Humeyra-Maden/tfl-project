package com.asiselectronics.tflappproject.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository = SettingsRepository()

) : ViewModel() {
    val currentLanguage: StateFlow<String> = repository.languageFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "tr"
        )
    fun setLanguage(languageCode:  String) {
        viewModelScope.launch {
            repository.setLanguage(languageCode)
        }
    }
}