package com.reutiliza.app.data.model

data class Chat(
    val id: String = "",
    val productId: String = "",
    val productTitle: String = "",
    val productPrice: Double = 0.0,
    val productTransactionType: String = "",
    val productImageUrl: String = "",
    val participantIds: List<String> = emptyList(),
    val participantNames: Map<String, String> = emptyMap(),
    val participantPhotos: Map<String, String> = emptyMap(),
    val sellerId: String = "",
    val buyerId: String = "",
    val otherUserName: String = "",
    val otherUserPhotoUrl: String = "",
    val lastMessage: String = "",
    val lastMessageTime: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0
) {
    fun getOtherUserName(currentUserId: String): String {
        val otherId = participantIds.firstOrNull { it != currentUserId }
        if (otherId != null && participantNames.containsKey(otherId) && !participantNames[otherId].isNullOrBlank()) {
            return participantNames[otherId]!!
        }
        if (otherUserName.isNotBlank() && otherUserName != "Usuario") return otherUserName
        return "Usuario"
    }

    fun getOtherUserPhoto(currentUserId: String): String {
        val otherId = participantIds.firstOrNull { it != currentUserId }
        if (otherId != null && participantPhotos.containsKey(otherId)) {
            return participantPhotos[otherId].orEmpty()
        }
        return otherUserPhotoUrl
    }
}

data class Message(
    val id: String = "",
    val chatId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val content: String = "",
    val type: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

enum class MessageType { TEXT, IMAGE }
