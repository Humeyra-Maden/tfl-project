package com.asiselectronics.tflappproject.data.repository


import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.asiselectronics.tflappproject.data.local.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository {
    private val dataStore = createDataStore()
    private val languageKey = stringPreferencesKey("app_language")

    val languageFlow: Flow<String> = dataStore.data.map { prefs ->
        prefs[languageKey] ?: "tr"
    }

    suspend fun setLanguage(languageCode: String) {
        dataStore.edit { prefs ->
            prefs[languageKey] = languageCode
        }
    }
}