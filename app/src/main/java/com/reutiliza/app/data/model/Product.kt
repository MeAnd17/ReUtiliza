package com.reutiliza.app.data.model

data class Product(
    val id: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val ownerPhotoUrl: String = "",
    val title: String = "",
    val description: String = "",
    val category: ProductCategory = ProductCategory.OTHER,
    val transactionType: TransactionType = TransactionType.DONATION,
    val condition: ProductCondition = ProductCondition.GOOD,
    val price: Double = 0.0,          // 0.0 para donaciones/intercambios
    val imageUrls: List<String> = emptyList(),
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val greenPointsReward: Int = 0,   // puntos que gana el donante/vendedor
    val isAvailable: Boolean = true,
    val isFavorite: Boolean = false,
    val favoritesCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ProductCategory(val displayName: String, val emoji: String) {
    CLOTHING    ("Ropa",         "👕"),
    BOOKS       ("Libros",       "📚"),
    FURNITURE   ("Muebles",      "🪑"),
    ELECTRONICS ("Electrónicos", "📱"),
    TOYS        ("Juguetes",     "🧸"),
    HOME        ("Hogar",        "🏠"),
    SPORTS      ("Deporte",      "⚽"),
    OTHER       ("Otros",        "📦")
}

enum class TransactionType(val displayName: String) {
    SALE      ("Venta"),
    DONATION  ("Donación"),
    EXCHANGE  ("Intercambio")
}

enum class ProductCondition(val displayName: String) {
    NEW        ("Nuevo"),
    LIKE_NEW   ("Como nuevo"),
    GOOD       ("Buen estado"),
    ACCEPTABLE ("Aceptable")
}
