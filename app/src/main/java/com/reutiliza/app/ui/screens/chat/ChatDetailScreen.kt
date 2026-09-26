package com.reutiliza.app.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.reutiliza.app.data.model.Message
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.utils.TimeUtils
import com.reutiliza.app.utils.rememberImageModel
import com.reutiliza.app.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatDetailScreen(
    chatId: String,
    onBack: () -> Unit,
    vm: ChatViewModel = hiltViewModel()
) {
    val messages     by vm.messages.collectAsState()
    val messageInput by vm.messageInput.collectAsState()
    val listState    = rememberLazyListState()
    val scope        = rememberCoroutineScope()

    LaunchedEffect(chatId) { vm.loadMessages(chatId) }
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch { listState.animateScrollToItem(messages.size - 1) }
        }
    }

    val chat = vm.chats.collectAsState().value.find { it.id == chatId }
    val (otherUserName, otherUserPhoto) = vm.getOtherUserForChat(chat)
    val userPhotoModel = rememberImageModel(otherUserPhoto)

    Column(modifier = Modifier.fillMaxSize().background(Green50).imePadding()) {
        // ── Top bar ───────────────────────────────────────────────────────
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Green500),
                contentAlignment = Alignment.Center
            ) {
                if (userPhotoModel != null) {
                    AsyncImage(
                        model = userPhotoModel,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape)
                    )
                } else {
                    Text(
                        otherUserName.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    otherUserName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    chat?.productTitle ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(color = NeutralGray400)
                )
            }
        }
        Divider(color = NeutralBorder)

        // ── Tarjeta de Producto en discusión ─────────────────────────────
        if (chat != null && chat.productTitle.isNotEmpty()) {
            val prodImgModel = rememberImageModel(chat.productImageUrl)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Green100),
                        contentAlignment = Alignment.Center
                    ) {
                        if (prodImgModel != null) {
                            AsyncImage(
                                model = prodImgModel,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text("📦", fontSize = 24.sp)
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = chat.productTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = if (chat.productPrice > 0) "${chat.productPrice.toInt()}€" else "Artículo en negociación",
                            style = MaterialTheme.typography.bodySmall.copy(color = Green500, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

        // ── Mensajes ──────────────────────────────────────────────────────
        LazyColumn(
            state           = listState,
            modifier        = Modifier.weight(1f).padding(horizontal = 16.dp),
            contentPadding  = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubble(
                    message   = message,
                    isFromMe  = message.senderId == vm.currentUserId
                )
            }
        }

        // ── Input ─────────────────────────────────────────────────────────
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value         = messageInput,
                onValueChange = { vm.messageInput.value = it },
                placeholder   = { Text("Escribe un mensaje...") },
                modifier      = Modifier.weight(1f),
                singleLine    = true,
                shape         = RoundedCornerShape(24.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = Green500,
                    unfocusedBorderColor    = NeutralBorder,
                    unfocusedContainerColor = Green50,
                    focusedContainerColor   = Green50
                )
            )
            Spacer(Modifier.width(8.dp))
            FloatingActionButton(
                onClick          = { vm.sendMessage(chatId) },
                containerColor   = Green500,
                contentColor     = Color.White,
                modifier         = Modifier.size(48.dp)
            ) {
                Icon(Icons.Filled.Send, "Enviar", modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun MessageBubble(message: Message, isFromMe: Boolean) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start,
            modifier            = Modifier.widthIn(max = 280.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart    = 16.dp,
                    topEnd      = 16.dp,
                    bottomStart = if (isFromMe) 16.dp else 4.dp,
                    bottomEnd   = if (isFromMe) 4.dp else 16.dp
                ),
                color = if (isFromMe) Green500 else Color.White,
                shadowElevation = 1.dp
            ) {
                Text(
                    text     = message.content,
                    style    = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isFromMe) Color.White else NeutralBlack
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                TimeUtils.formatTime(message.timestamp),
                style = MaterialTheme.typography.labelSmall.copy(color = NeutralGray400)
            )
        }
    }
}
