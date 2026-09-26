package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val productRepo: ProductRepository
) : ViewModel() {

    private val _products     = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading    = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val searchQuery           = MutableStateFlow("")
    val selectedCategory      = MutableStateFlow<ProductCategory?>(null)
    val selectedTransactionType = MutableStateFlow<TransactionType?>(null)

    init {
        viewModelScope.launch {
            searchQuery.debounce(400).collect { search() }
        }
        search()
    }

    fun search() {
        viewModelScope.launch {
            _isLoading.value = true
            productRepo.searchProducts(
                query           = searchQuery.value,
                category        = selectedCategory.value,
                transactionType = selectedTransactionType.value
            ).fold(
                onSuccess = { _products.value = it },
                onFailure = { /* manejo de error */ }
            )
            _isLoading.value = false
        }
    }

    fun selectCategory(cat: ProductCategory?) {
        selectedCategory.value = cat
        search()
    }

    fun selectTransactionType(type: TransactionType?) {
        selectedTransactionType.value = type
        search()
    }
}
