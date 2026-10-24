package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reutiliza.app.data.model.Chat
import com.reutiliza.app.data.model.Message
import com.reutiliza.app.data.model.MessageType
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.repository.AuthRepository
import com.reutiliza.app.data.repository.ChatRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepo: ChatRepository,
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _chats      = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    private val _messages   = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _otherUsers = MutableStateFlow<Map<String, User>>(emptyMap())
    val otherUsers: StateFlow<Map<String, User>> = _otherUsers

    val messageInput = MutableStateFlow("")

    private var _currentUserId: String? = null
    val currentUserId: String get() = _currentUserId ?: ""

    init {
        viewModelScope.launch {
            _currentUserId = authRepo.getCurrentUserId()
            loadChats()
        }
    }

    private fun loadChats() {
        val uid = _currentUserId ?: return
        viewModelScope.launch {
            chatRepo.getUserChats(uid).collect { chatList ->
                _chats.value = chatList
                chatList.forEach { chat ->
                    val otherId = chat.participantIds.firstOrNull { it != uid }
                        ?: if (chat.sellerId != uid) chat.sellerId else chat.buyerId
                    if (otherId.isNotBlank() && !_otherUsers.value.containsKey(otherId)) {
                        viewModelScope.launch {
                            userRepo.getUser(otherId).onSuccess { user ->
                                _otherUsers.value = _otherUsers.value + (otherId to user)
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
        val otherId = chat.participantIds.firstOrNull { it != uid }
            ?: if (chat.sellerId != uid) chat.sellerId else chat.buyerId
        val fetchedUser = _otherUsers.value[otherId]
        val name  = fetchedUser?.name.orEmpty().ifEmpty { chat.getOtherUserName(uid) }
        val photo = fetchedUser?.photoUrl.orEmpty().ifEmpty { chat.getOtherUserPhoto(uid) }
        return Pair(name, photo)
    }

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            // Carga inicial
            chatRepo.getChatMessages(chatId).collect { _messages.value = it }
        }
        // Polling cada 5 segundos para simular tiempo real
        viewModelScope.launch {
            while (true) {
                delay(5000)
                chatRepo.getChatMessages(chatId).collect { _messages.value = it }
            }
        }
    }

    fun sendMessage(chatId: String) {
        val content = messageInput.value.trim()
        if (content.isBlank()) return
        val uid = _currentUserId ?: return
        messageInput.value = ""

        val message = Message(
            chatId     = chatId,
            senderId   = uid,
            senderName = "Tú",
            content    = content,
            type       = MessageType.TEXT
        )
        viewModelScope.launch {
            chatRepo.sendMessage(chatId, message)
        }
    }
}
