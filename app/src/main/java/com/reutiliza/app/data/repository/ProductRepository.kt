package com.reutiliza.app.data.repository

import com.reutiliza.app.data.local.SessionManager
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.data.remote.CreateProductRequest
import com.reutiliza.app.data.remote.ReUtilizaApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val api: ReUtilizaApiService,
    private val session: SessionManager
) {
    /** Emite la lista de productos recientes una vez */
    fun getRecentProducts(limit: Long = 20): Flow<List<Product>> = flow {
        runCatching { api.getProducts(limit.toInt()) }
            .onSuccess { emit(it) }
            .onFailure { emit(emptyList()) }
    }

    suspend fun searchProducts(
        query: String = "",
        category: ProductCategory? = null,
        transactionType: TransactionType? = null
    ): Result<List<Product>> = runCatching {
        api.searchProducts(
            query          = query,
            category       = category?.name,
            transactionType = transactionType?.name
        )
    }

    suspend fun getProductById(id: String): Result<Product> = runCatching {
        api.getProductById(id)
    }

    suspend fun publishProduct(product: Product, imageBytes: List<ByteArray>): Result<String> =
        runCatching {
            val token = session.getToken() ?: error("No autenticado")
            val request = CreateProductRequest(
                title           = product.title,
                description     = product.description,
                category        = product.category.name,
                transactionType = product.transactionType.name,
                condition       = product.condition.name,
                price           = product.price,
                location        = product.location,
                latitude        = product.latitude,
                longitude       = product.longitude
            )
            val response = api.createProduct(session.bearerToken(token), request)
            response.productId
        }

    suspend fun toggleFavorite(productId: String, userId: String, isFav: Boolean): Result<Unit> =
        runCatching {
            val token = session.getToken() ?: error("No autenticado")
            if (isFav) api.addFavorite(session.bearerToken(token), productId)
            else api.removeFavorite(session.bearerToken(token), productId)
        }

    fun getUserProducts(userId: String): Flow<List<Product>> = flow {
        val token = session.getToken() ?: run { emit(emptyList()); return@flow }
        runCatching { api.getUserProducts(session.bearerToken(token), userId) }
            .onSuccess { emit(it) }
            .onFailure { emit(emptyList()) }
    }
}
