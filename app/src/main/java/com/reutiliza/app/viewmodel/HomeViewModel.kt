package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.repository.AuthRepository
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
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _recentProducts = MutableStateFlow<List<Product>>(emptyList())
    val recentProducts: StateFlow<List<Product>> = _recentProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Cargar usuario actual
            val userId = authRepo.getCurrentUserId()
            if (userId != null) {
                userRepo.getUserFlow(userId).collect { _currentUser.value = it }
            }
        }
        viewModelScope.launch {
            // Cargar productos recientes desde el backend
            productRepo.getRecentProducts().collect {
                _recentProducts.value = it
                _isLoading.value = false
            }
        }
    }
}
