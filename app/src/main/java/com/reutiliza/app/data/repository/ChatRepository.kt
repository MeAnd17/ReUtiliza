package com.reutiliza.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.reutiliza.app.data.model.Chat
import com.reutiliza.app.data.model.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val chatsRef    = firestore.collection("chats")
    private val messagesRef = firestore.collection("messages")

    /** Chats del usuario actual en tiempo real */
    fun getUserChats(userId: String): Flow<List<Chat>> = callbackFlow {
        val listener = chatsRef
            .whereArrayContains("participantIds", userId)
            .addSnapshotListener { snap, err ->
                if (err != null) { trySend(emptyList()); return@addSnapshotListener }
                val list = snap?.documents?.mapNotNull { it.toObject(Chat::class.java) }
                    ?.sortedByDescending { it.lastMessageTime }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    /** Mensajes de un chat en tiempo real */
    fun getChatMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val listener = messagesRef
            .whereEqualTo("chatId", chatId)
            .addSnapshotListener { snap, err ->
                if (err != null) { trySend(emptyList()); return@addSnapshotListener }
                val list = snap?.documents?.mapNotNull { it.toObject(Message::class.java) }
                    ?.sortedBy { it.timestamp }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    /** Crear o recuperar un chat entre dos usuarios sobre un producto */
    suspend fun getOrCreateChat(
        buyerUser: com.reutiliza.app.data.model.User,
        sellerUser: com.reutiliza.app.data.model.User?,
        product: com.reutiliza.app.data.model.Product
    ): Result<String> = runCatching {
        val buyerId = buyerUser.id
        val sellerId = product.ownerId

        // Buscar si ya existe recuperando los chats del usuario y filtrando localmente
        val existing = chatsRef
            .whereArrayContains("participantIds", buyerId)
            .get().await()

        val existingDoc = existing.documents.firstOrNull { doc ->
            val chat = doc.toObject(Chat::class.java)
            chat?.productId == product.id && chat.participantIds.contains(sellerId)
        }

        if (existingDoc != null) return@runCatching existingDoc.id

        val chatId = UUID.randomUUID().toString()
        val sellerName = sellerUser?.name.orEmpty().ifEmpty {
            product.ownerName.ifEmpty { "Vendedor" }
        }

        val participantNames = mapOf(
            buyerId to buyerUser.name.ifEmpty { "Comprador" },
            sellerId to sellerName
        )
        val participantPhotos = mapOf(
            buyerId to buyerUser.photoUrl,
            sellerId to sellerUser?.photoUrl.orEmpty().ifEmpty { product.ownerPhotoUrl }
        )

        val defaultMsgText = when (product.transactionType) {
            com.reutiliza.app.data.model.TransactionType.DONATION ->
                "¡Hola! Me interesa tu donación de \"${product.title}\". ¿Sigue disponible? 🎁"
            com.reutiliza.app.data.model.TransactionType.EXCHANGE ->
                "¡Hola! Me interesa intercambiar por \"${product.title}\". ¿Sigue disponible? 🔄"
            com.reutiliza.app.data.model.TransactionType.SALE ->
                "¡Hola! Estoy interesado en comprar \"${product.title}\". ¿Sigue disponible? 💰"
        }

        val chat = Chat(
            id = chatId,
            productId = product.id,
            productTitle = product.title,
            productPrice = product.price,
            productTransactionType = product.transactionType.name,
            productImageUrl = product.imageUrls.firstOrNull() ?: "",
            participantIds = listOf(buyerId, sellerId),
            participantNames = participantNames,
            participantPhotos = participantPhotos,
            sellerId = sellerId,
            buyerId = buyerId,
            otherUserName = sellerName,
            otherUserPhotoUrl = product.ownerPhotoUrl,
            lastMessage = defaultMsgText,
            lastMessageTime = System.currentTimeMillis()
        )
        chatsRef.document(chatId).set(chat).await()

        // Enviar el primer mensaje automático predeterminado
        val firstMsg = Message(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            senderId = buyerId,
            senderName = buyerUser.name.ifEmpty { "Comprador" },
            content = defaultMsgText,
            timestamp = System.currentTimeMillis()
        )
        messagesRef.document(firstMsg.id).set(firstMsg).await()

        chatId
    }

    suspend fun sendMessage(chatId: String, message: Message): Result<Unit> = runCatching {
        val msgId = UUID.randomUUID().toString()
        val finalMsg = message.copy(id = msgId, chatId = chatId)
        messagesRef.document(msgId).set(finalMsg).await()
        // Actualizar último mensaje del chat
        chatsRef.document(chatId).update(
            mapOf(
                "lastMessage"     to message.content,
                "lastMessageTime" to message.timestamp
            )
        ).await()
    }
}
