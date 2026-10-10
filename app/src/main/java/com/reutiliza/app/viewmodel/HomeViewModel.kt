package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.remote.EcoTipDto
import com.reutiliza.app.data.remote.EcoApiService
import com.reutiliza.app.data.repository.ProductRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val userRepo: UserRepository,
    private val ecoApiService: EcoApiService,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _recentProducts = MutableStateFlow<List<Product>>(emptyList())
    val recentProducts: StateFlow<List<Product>> = _recentProducts

    private val _ecoTips = MutableStateFlow<List<EcoTipDto>>(emptyList())
    val ecoTips: StateFlow<List<EcoTipDto>> = _ecoTips

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadData()
        loadEcoTips()
    }

    fun loadData() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            // Usuario actual
            userRepo.getUserFlow(uid).collect { _currentUser.value = it }
        }
        viewModelScope.launch {
            // Productos recientes
            productRepo.getRecentProducts().collect { _recentProducts.value = it }
        }
    }

    fun loadEcoTips() {
        viewModelScope.launch {
            runCatching {
                ecoApiService.getEcoTips()
            }.onSuccess { tips ->
                _ecoTips.value = tips
            }
        }
    }
}
