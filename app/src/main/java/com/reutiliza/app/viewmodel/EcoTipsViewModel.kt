package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.remote.EcoTipDto
import com.reutiliza.app.data.repository.ApiResult
import com.reutiliza.app.data.repository.EcoTipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Estado global de la pantalla CRUD */
data class EcoTipsUiState(
    val tips: List<EcoTipDto>    = emptyList(),
    val isLoading: Boolean       = false,
    val errorMessage: String?    = null,
    val successMessage: String?  = null,
    val isOffline: Boolean       = false
)

@HiltViewModel
class EcoTipsViewModel @Inject constructor(
    private val repository: EcoTipRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EcoTipsUiState())
    val uiState: StateFlow<EcoTipsUiState> = _uiState

    init {
        loadTips()
    }

    // ── READ ──────────────────────────────────────────────────────────────────

    fun loadTips() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.getEcoTips()) {
                is ApiResult.Success -> _uiState.value = _uiState.value.copy(
                    tips      = result.data,
                    isLoading = false,
                    isOffline = false
                )
                is ApiResult.Error   -> _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = result.message,
                    isOffline    = result.type == com.reutiliza.app.data.repository.ErrorType.NO_CONNECTION
                )
                else -> Unit
            }
        }
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    fun createTip(title: String, body: String) {
        if (title.isBlank() || body.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Título y descripción son requeridos.")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.createEcoTip(title.trim(), body.trim())) {
                is ApiResult.Success -> {
                    // JSONPlaceholder devuelve el objeto creado con id=101
                    val updated = listOf(result.data) + _uiState.value.tips
                    _uiState.value = _uiState.value.copy(
                        tips           = updated,
                        isLoading      = false,
                        successMessage = "✅ Eco-tip creado exitosamente (id: ${result.data.id})"
                    )
                }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = result.message
                )
                else -> Unit
            }
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    fun updateTip(id: Int, title: String, body: String) {
        if (title.isBlank() || body.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Título y descripción son requeridos.")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.updateEcoTip(id, title.trim(), body.trim())) {
                is ApiResult.Success -> {
                    val updated = _uiState.value.tips.map {
                        if (it.id == id) result.data else it
                    }
                    _uiState.value = _uiState.value.copy(
                        tips           = updated,
                        isLoading      = false,
                        successMessage = "✅ Eco-tip actualizado exitosamente"
                    )
                }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = result.message
                )
                else -> Unit
            }
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    fun deleteTip(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.deleteEcoTip(id)) {
                is ApiResult.Success -> {
                    val updated = _uiState.value.tips.filter { it.id != id }
                    _uiState.value = _uiState.value.copy(
                        tips           = updated,
                        isLoading      = false,
                        successMessage = "🗑️ Eco-tip eliminado"
                    )
                }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = result.message
                )
                else -> Unit
            }
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }
}
