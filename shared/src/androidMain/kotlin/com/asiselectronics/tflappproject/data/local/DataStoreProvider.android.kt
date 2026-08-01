package com.asiselectronics.tflappproject.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private lateinit var appContext: Context
private val Context.dataStore by preferencesDataStore(name = "app_settings")

fun initDataStore(context: Context) {
    appContext = context.applicationContext
}

actual fun createDataStore(): DataStore<Preferences> = appContext.dataStore