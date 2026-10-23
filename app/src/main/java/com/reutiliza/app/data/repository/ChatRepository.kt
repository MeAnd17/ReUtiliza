package com.reutiliza.app.data.repository

import com.reutiliza.app.data.local.SessionManager
import com.reutiliza.app.data.model.Chat
import com.reutiliza.app.data.model.Message
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.remote.CreateChatRequest
import com.reutiliza.app.data.remote.ReUtilizaApiService
import com.reutiliza.app.data.remote.SendMessageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val api: ReUtilizaApiService,
    private val session: SessionManager
) {
    fun getUserChats(userId: String): Flow<List<Chat>> = flow {
        val token = session.getToken() ?: run { emit(emptyList()); return@flow }
        // El backend devuelve chats del usuario autenticado via el token
        // Emitimos vacío si falla (sin crashear)
        emit(emptyList()) // placeholder — sin WebSocket no hay tiempo real
    }

    fun getChatMessages(chatId: String): Flow<List<Message>> = flow {
        emit(emptyList()) // sin WebSocket, se refresca manualmente
    }

    suspend fun getOrCreateChat(
        buyerUser: User,
        sellerUser: User?,
        product: Product
    ): Result<String> = runCatching {
        val token = session.getToken() ?: error("No autenticado")
        val response = api.getOrCreateChat(
            session.bearerToken(token),
            CreateChatRequest(
                productId = product.id,
                buyerId   = buyerUser.id,
                sellerId  = product.ownerId
            )
        )
        response.chatId
    }

    suspend fun sendMessage(chatId: String, message: Message): Result<Unit> = runCatching {
        val token = session.getToken() ?: error("No autenticado")
        api.sendMessage(
            session.bearerToken(token),
            chatId,
            SendMessageRequest(content = message.content)
        )
    }
}
