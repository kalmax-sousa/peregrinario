package com.example.peregrinario.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NOTIFICATIONS = booleanPreferencesKey("notifications")
    val DISPLAY_ANIMATIONS = booleanPreferencesKey("display_animations")
}

class PreferencesRepository (private val context: Context) {
    val darkModeFlow : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DARK_MODE] ?: false }

    val notificationsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATIONS] ?: true }

    val displayAnimationsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DISPLAY_ANIMATIONS] ?: true }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = enabled
        }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS] = enabled
        }
    }

    suspend fun setDisplayAnimations(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_ANIMATIONS] = enabled
        }
    }
}