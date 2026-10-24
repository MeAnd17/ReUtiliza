package com.reutiliza.app.data.remote

import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.User
import retrofit2.http.*

// ── Auth ─────────────────────────────────────────────────────────────────────

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class AuthResponse(val token: String, val user: User)

// ── Productos ────────────────────────────────────────────────────────────────

data class CreateProductRequest(
    val title: String,
    val description: String,
    val category: String,
    val transactionType: String,
    val condition: String,
    val price: Double,
    val location: String,
    val latitude: Double,
    val longitude: Double
)

data class CreateProductResponse(val productId: String, val product: Product)

// ── Chat / Mensajes ──────────────────────────────────────────────────────────

data class CreateChatRequest(
    val productId: String,
    val buyerId: String,
    val sellerId: String
)

data class CreateChatResponse(val chatId: String, val created: Boolean)

data class SendMessageRequest(val content: String, val type: String = "TEXT")

// ── Perfil ───────────────────────────────────────────────────────────────────

data class UpdateProfileRequest(val name: String, val bio: String)
data class GreenPointsRequest(val points: Int)

// ── Interfaz Retrofit ────────────────────────────────────────────────────────

interface ReUtilizaApiService {

    // Auth
    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @GET("api/auth/me")
    suspend fun me(@Header("Authorization") token: String): User

    // Productos
    @GET("api/products")
    suspend fun getProducts(@Query("limit") limit: Int = 20): List<Product>

    @GET("api/products/search")
    suspend fun searchProducts(
        @Query("query") query: String = "",
        @Query("category") category: String? = null,
        @Query("transactionType") transactionType: String? = null
    ): List<Product>

    @GET("api/products/{productId}")
    suspend fun getProductById(@Path("productId") productId: String): Product

    @POST("api/products")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body body: CreateProductRequest
    ): CreateProductResponse

    @GET("api/products/user/{userId}")
    suspend fun getUserProducts(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): List<Product>

    // Favoritos
    @PUT("api/favorites/{productId}")
    suspend fun addFavorite(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    )

    @DELETE("api/favorites/{productId}")
    suspend fun removeFavorite(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    )

    // Usuarios
    @GET("api/users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): User

    @PUT("api/users/{userId}/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body body: UpdateProfileRequest
    ): User

    @PATCH("api/users/{userId}/green-points")
    suspend fun addGreenPoints(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body body: GreenPointsRequest
    )

    @GET("api/users/leaderboard")
    suspend fun getLeaderboard(@Header("Authorization") token: String): List<User>

    // Chats
    @GET("api/chats")
    suspend fun getUserChats(
        @Header("Authorization") token: String
    ): List<com.reutiliza.app.data.model.Chat>

    @POST("api/chats")
    suspend fun getOrCreateChat(
        @Header("Authorization") token: String,
        @Body body: CreateChatRequest
    ): CreateChatResponse

    // Mensajes
    @GET("api/messages/{chatId}")
    suspend fun getChatMessages(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    ): List<com.reutiliza.app.data.model.Message>

    @POST("api/messages/{chatId}")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Body body: SendMessageRequest
    )

    companion object {
        const val BASE_URL = "https://reutiliza.onrender.com/"
    }
}
