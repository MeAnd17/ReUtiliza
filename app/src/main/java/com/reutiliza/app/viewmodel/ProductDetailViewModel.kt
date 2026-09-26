package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.repository.ChatRepository
import com.reutiliza.app.data.repository.ProductRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val userRepo: UserRepository,
    private val chatRepo: ChatRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _product  = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _chatId = MutableStateFlow<String?>(null)
    val chatId: StateFlow<String?> = _chatId

    val currentUserId: String? get() = auth.currentUser?.uid

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            productRepo.getProductById(productId).fold(
                onSuccess = { prod ->
                    var resolvedProd = prod
                    // Si el nombre guardado fue "Usuario" o está vacío, obtener el nombre real del creador
                    if (prod.ownerName == "Usuario" || prod.ownerName.isBlank()) {
                        val ownerUser = userRepo.getUser(prod.ownerId).getOrNull()
                        val realName = ownerUser?.name.orEmpty().ifEmpty {
                            if (auth.currentUser?.uid == prod.ownerId) {
                                auth.currentUser?.displayName.orEmpty().ifEmpty {
                                    auth.currentUser?.email?.substringBefore("@") ?: ""
                                }
                            } else ""
                        }
                        if (realName.isNotBlank() && realName != "Usuario") {
                            resolvedProd = prod.copy(
                                ownerName = realName,
                                ownerPhotoUrl = ownerUser?.photoUrl ?: prod.ownerPhotoUrl
                            )
                        }
                    }
                    _product.value = resolvedProd
                },
                onFailure = { }
            )
            _isLoading.value = false
        }
    }

    fun contactSeller() {
        val product = _product.value ?: return
        val uid     = auth.currentUser?.uid ?: return
        if (uid == product.ownerId) return  // no puedes chatear contigo mismo

        viewModelScope.launch {
            val buyerUser = userRepo.getUser(uid).getOrNull() ?: com.reutiliza.app.data.model.User(
                id = uid,
                name = auth.currentUser?.displayName.orEmpty().ifEmpty {
                    auth.currentUser?.email?.substringBefore("@") ?: "Comprador"
                }
            )
            val sellerUser = userRepo.getUser(product.ownerId).getOrNull()

            chatRepo.getOrCreateChat(buyerUser, sellerUser, product).fold(
                onSuccess = { _chatId.value = it },
                onFailure = { }
            )
        }
    }

    fun toggleFavorite() {
        val product = _product.value ?: return
        val uid     = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            productRepo.toggleFavorite(product.id, uid, !product.isFavorite)
            _product.value = product.copy(isFavorite = !product.isFavorite)
        }
    }

    fun clearChatId() { _chatId.value = null }
}
