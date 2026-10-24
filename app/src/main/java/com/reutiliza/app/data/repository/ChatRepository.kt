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
        runCatching { api.getUserChats(session.bearerToken(token)) }
            .onSuccess { emit(it) }
            .onFailure { emit(emptyList()) }
    }

    fun getChatMessages(chatId: String): Flow<List<Message>> = flow {
        val token = session.getToken() ?: run { emit(emptyList()); return@flow }
        runCatching { api.getChatMessages(session.bearerToken(token), chatId) }
            .onSuccess { emit(it) }
            .onFailure { emit(emptyList()) }
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
