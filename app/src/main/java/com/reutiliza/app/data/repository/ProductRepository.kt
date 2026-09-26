package com.reutiliza.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.TransactionType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    private val productsRef = firestore.collection("products")

    /** Flujo en tiempo real de los productos más recientes */
    fun getRecentProducts(limit: Long = 20): Flow<List<Product>> = callbackFlow {
        val listener = productsRef
            .whereEqualTo("available", true)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snap?.documents
                    ?.mapNotNull { it.toObject(Product::class.java) }
                    ?.sortedByDescending { it.createdAt }
                    ?.take(limit.toInt())
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    /** Búsqueda local simple por título */
    suspend fun searchProducts(
        query: String = "",
        category: ProductCategory? = null,
        transactionType: TransactionType? = null
    ): Result<List<Product>> = runCatching {
        var ref: Query = productsRef.whereEqualTo("available", true)
        category?.let        { ref = ref.whereEqualTo("category", it.name) }
        transactionType?.let { ref = ref.whereEqualTo("transactionType", it.name) }
        val docs = ref.get().await()
        docs.mapNotNull { it.toObject(Product::class.java) }
            .filter { query.isEmpty() || it.title.contains(query, ignoreCase = true) }
            .sortedByDescending { it.createdAt }
    }

    suspend fun getProductById(id: String): Result<Product> = runCatching {
        val doc = productsRef.document(id).get().await()
        doc.toObject(Product::class.java) ?: error("Producto no encontrado")
    }

    suspend fun publishProduct(product: Product, imageBytes: List<ByteArray>): Result<String> =
        runCatching {
            val productId = UUID.randomUUID().toString()
            
            val imageUrls = imageBytes.mapIndexedNotNull { i, bytes ->
                runCatching {
                    val ref = storage.reference.child("products/$productId/image_$i.jpg")
                    ref.putBytes(bytes).await()
                    ref.downloadUrl.await().toString()
                }.getOrElse {
                    compressAndToBase64(bytes)
                }
            }.filter { it.isNotEmpty() }

            val finalProduct = product.copy(id = productId, imageUrls = imageUrls)
            productsRef.document(productId).set(finalProduct).await()
            productId
        }

    private fun compressAndToBase64(bytes: ByteArray): String {
        return runCatching {
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: return ""
            val maxDim = 600
            val width = bitmap.width
            val height = bitmap.height
            val scale = Math.min(maxDim.toFloat() / width, maxDim.toFloat() / height).coerceAtMost(1.0f)
            val scaledWidth = (width * scale).toInt().coerceAtLeast(1)
            val scaledHeight = (height * scale).toInt().coerceAtLeast(1)
            val scaledBitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
            val baos = java.io.ByteArrayOutputStream()
            scaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 70, baos)
            "data:image/jpeg;base64," + android.util.Base64.encodeToString(baos.toByteArray(), android.util.Base64.NO_WRAP)
        }.getOrDefault("")
    }

    suspend fun toggleFavorite(productId: String, userId: String, isFav: Boolean): Result<Unit> =
        runCatching {
            val favRef = firestore.collection("favorites")
                .document("${userId}_$productId")
            if (isFav) favRef.set(mapOf("userId" to userId, "productId" to productId)).await()
            else       favRef.delete().await()
        }

    fun getUserProducts(userId: String): Flow<List<Product>> = callbackFlow {
        val listener = productsRef
            .whereEqualTo("ownerId", userId)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snap?.documents
                    ?.mapNotNull { it.toObject(Product::class.java) }
                    ?.sortedByDescending { it.createdAt }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }
}
