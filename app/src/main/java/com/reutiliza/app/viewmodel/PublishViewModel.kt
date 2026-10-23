package com.reutiliza.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.ProductCondition
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.data.repository.AuthRepository
import com.reutiliza.app.data.repository.ProductRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PublishUiState {
    object Idle    : PublishUiState()
    object Loading : PublishUiState()
    object Success : PublishUiState()
    data class Error(val message: String) : PublishUiState()
}

@HiltViewModel
class PublishViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PublishUiState>(PublishUiState.Idle)
    val uiState: StateFlow<PublishUiState> = _uiState

    val title                   = MutableStateFlow("")
    val description             = MutableStateFlow("")
    val price                   = MutableStateFlow("")
    val selectedCategory        = MutableStateFlow(ProductCategory.OTHER)
    val selectedTransactionType = MutableStateFlow(TransactionType.DONATION)
    val selectedCondition       = MutableStateFlow(ProductCondition.GOOD)
    val selectedImages          = MutableStateFlow<List<Uri>>(emptyList())

    fun addImage(uri: Uri) {
        if (selectedImages.value.size < 5) {
            selectedImages.value = selectedImages.value + uri
        }
    }

    fun removeImage(uri: Uri) {
        selectedImages.value = selectedImages.value - uri
    }

    fun publish(context: android.content.Context) {
        if (title.value.isBlank()) {
            _uiState.value = PublishUiState.Error("El título es obligatorio")
            return
        }

        viewModelScope.launch {
            val uid = authRepo.getCurrentUserId()
            if (uid == null) {
                _uiState.value = PublishUiState.Error("Debes iniciar sesión para publicar")
                return@launch
            }
            _uiState.value = PublishUiState.Loading
            try {
                val imageBytes = selectedImages.value.mapNotNull { uri ->
                    runCatching {
                        context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                    }.getOrNull()
                }

                val greenPoints = when (selectedTransactionType.value) {
                    TransactionType.DONATION -> 30
                    TransactionType.EXCHANGE -> 20
                    TransactionType.SALE     -> 15
                }

                val authorUser = userRepo.getUser(uid).getOrNull()

                val product = Product(
                    ownerId           = uid,
                    ownerName         = authorUser?.name ?: "Usuario",
                    ownerPhotoUrl     = authorUser?.photoUrl ?: "",
                    title             = title.value.trim(),
                    description       = description.value.trim(),
                    category          = selectedCategory.value,
                    transactionType   = selectedTransactionType.value,
                    condition         = selectedCondition.value,
                    price             = price.value.toDoubleOrNull() ?: 0.0,
                    greenPointsReward = greenPoints
                )

                productRepo.publishProduct(product, imageBytes).fold(
                    onSuccess = {
                        runCatching { userRepo.addGreenPoints(uid, greenPoints) }
                        _uiState.value = PublishUiState.Success
                    },
                    onFailure = { error ->
                        _uiState.value = PublishUiState.Error(
                            error.localizedMessage ?: "Error al publicar el artículo"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = PublishUiState.Error(e.localizedMessage ?: "Error inesperado")
            }
        }
    }

    fun resetState() { _uiState.value = PublishUiState.Idle }
}
