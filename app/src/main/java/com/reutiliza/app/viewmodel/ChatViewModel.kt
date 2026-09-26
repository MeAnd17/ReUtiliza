package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reutiliza.app.data.model.Chat
import com.reutiliza.app.data.model.Message
import com.reutiliza.app.data.model.MessageType
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.repository.ChatRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepo: ChatRepository,
    private val userRepo: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _otherUsers = MutableStateFlow<Map<String, User>>(emptyMap())
    val otherUsers: StateFlow<Map<String, User>> = _otherUsers

    val messageInput = MutableStateFlow("")
    val currentUserId: String get() = auth.currentUser?.uid ?: ""

    init {
        loadChats()
    }

    private fun loadChats() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            chatRepo.getUserChats(uid).collect { chatList ->
                _chats.value = chatList
                // Cargar dinámicamente los perfiles reales de los otros participantes
                chatList.forEach { chat ->
                    var otherId = chat.participantIds.firstOrNull { it != uid }
                    if (otherId.isNullOrBlank()) {
                        otherId = if (chat.sellerId.isNotBlank() && chat.sellerId != uid) chat.sellerId
                                  else if (chat.buyerId.isNotBlank() && chat.buyerId != uid) chat.buyerId
                                  else ""
                    }
                    if (otherId.isNotBlank() && !_otherUsers.value.containsKey(otherId)) {
                        viewModelScope.launch {
                            userRepo.getUser(otherId).onSuccess { fetchedUser ->
                                _otherUsers.value = _otherUsers.value + (otherId to fetchedUser)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getOtherUserForChat(chat: Chat?): Pair<String, String> {
        if (chat == null) return Pair("Chat", "")
        val uid = currentUserId
        var otherId = chat.participantIds.firstOrNull { it != uid }
        if (otherId.isNullOrBlank()) {
            otherId = if (chat.sellerId.isNotBlank() && chat.sellerId != uid) chat.sellerId
                      else if (chat.buyerId.isNotBlank() && chat.buyerId != uid) chat.buyerId
                      else ""
        }

        val fetchedUser = _otherUsers.value[otherId]
        val name = fetchedUser?.name.orEmpty().ifEmpty {
            chat.getOtherUserName(uid)
        }
        val photo = fetchedUser?.photoUrl.orEmpty().ifEmpty {
            chat.getOtherUserPhoto(uid)
        }
        return Pair(name, photo)
    }

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            chatRepo.getChatMessages(chatId).collect { _messages.value = it }
        }
    }

    fun sendMessage(chatId: String) {
        val content = messageInput.value.trim()
        if (content.isBlank()) return
        val uid = auth.currentUser?.uid ?: return
        messageInput.value = ""

        val message = Message(
            chatId     = chatId,
            senderId   = uid,
            senderName = auth.currentUser?.displayName ?: "Tú",
            content    = content,
            type       = MessageType.TEXT
        )
        viewModelScope.launch {
            chatRepo.sendMessage(chatId, message)
        }
    }
}
