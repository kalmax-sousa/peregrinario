package com.example.peregrinario.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peregrinario.models.User
import com.example.peregrinario.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

class AuthViewModel (private val repository: AuthRepository) : ViewModel() {
    var loginResult: ((Boolean) -> Unit) ?= null
    var registerResult: ((Boolean) -> Unit)? = null

    fun register(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.registerUser(email, password, name)
            onResult(success)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginUser(email, password)
            onResult(success) // Retorna true ou false para a tela de login
        }
    }

    fun loginWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginWithGoogle(idToken)
            onResult(success)
        }
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        return repository.getGoogleSignInClient(context)
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun resetPassword(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.resetPassword(email)
            onResult(success)
        }
    }

    fun getUser(onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUser()
            onResult(user)
        }
    }

}