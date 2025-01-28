package com.example.peregrinario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peregrinario.repository.PreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class PreferencesViewModel (private val preferencesRepository: PreferencesRepository) : ViewModel() {

    val darkMode : StateFlow<Boolean> = preferencesRepository.darkModeFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, false
    )

    val notifications : StateFlow<Boolean> = preferencesRepository.notificationsFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )

    val displayAnimations : StateFlow<Boolean> = preferencesRepository.displayAnimationsFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )

    fun setDarkMode(enabled: Boolean){
        viewModelScope.launch {
            preferencesRepository.setDarkMode(enabled)
        }
    }

    fun setNotifications(enabled: Boolean){
        viewModelScope.launch {
            preferencesRepository.setNotifications(enabled)
        }
    }

    fun setDisplayAnimations(enabled: Boolean){
        viewModelScope.launch {
            preferencesRepository.setDisplayAnimations(enabled)
        }
    }
}