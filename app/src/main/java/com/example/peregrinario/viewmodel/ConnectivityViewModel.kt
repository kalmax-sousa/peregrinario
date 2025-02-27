package com.example.peregrinario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peregrinario.models.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ConnectivityViewModel(connectivityObserver: ConnectivityObserver) : ViewModel() {
    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        connectivityObserver.observe()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectivityObserver.Status.Available)
}
