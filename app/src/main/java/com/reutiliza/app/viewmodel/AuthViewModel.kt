package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    object Idle    : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User? = null) : AuthUiState()
    data class Error(val message: String)      : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    val isLoggedIn: Boolean get() = authRepo.isLoggedIn

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Completa todos los campos")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepo.login(email.trim(), password).fold(
                onSuccess = { _uiState.value = AuthUiState.Success() },
                onFailure = { _uiState.value = AuthUiState.Error(it.message ?: "Error al iniciar sesión") }
            )
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        when {
            name.isBlank() || email.isBlank() || password.isBlank() ->
                _uiState.value = AuthUiState.Error("Completa todos los campos")
            password != confirmPassword ->
                _uiState.value = AuthUiState.Error("Las contraseñas no coinciden")
            password.length < 6 ->
                _uiState.value = AuthUiState.Error("La contraseña debe tener al menos 6 caracteres")
            else -> viewModelScope.launch {
                _uiState.value = AuthUiState.Loading
                authRepo.register(name.trim(), email.trim(), password).fold(
                    onSuccess = { _uiState.value = AuthUiState.Success() },
                    onFailure = { _uiState.value = AuthUiState.Error(it.message ?: "Error al registrarse") }
                )
            }
        }
    }

    fun logout() = authRepo.logout()
    fun resetState() { _uiState.value = AuthUiState.Idle }
}
