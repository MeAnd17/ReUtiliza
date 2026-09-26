package com.reutiliza.app.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.reutiliza.app.data.model.Chat
import com.reutiliza.app.ui.components.TopBarReUtiliza
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.utils.TimeUtils
import com.reutiliza.app.utils.rememberImageModel
import com.reutiliza.app.viewmodel.ChatViewModel

@Composable
fun ChatsScreen(
    onChatClick: (String) -> Unit,
    vm: ChatViewModel = hiltViewModel()
) {
    val chats by vm.chats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green50)
    ) {
        TopBarReUtiliza(title = "Mensajes")

        if (chats.isEmpty()) {
            Box(
                modifier           = Modifier.fillMaxSize(),
                contentAlignment   = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("💬", fontSize = 56.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Aún no tienes mensajes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Contacta al dueño de un artículo\npara iniciar una conversación",
                        style     = MaterialTheme.typography.bodyMedium,
                        color     = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding      = PaddingValues(bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(chats) { chat ->
                    val (otherName, otherPhoto) = vm.getOtherUserForChat(chat)
                    ChatItem(chat = chat, otherUserName = otherName, otherUserPhoto = otherPhoto, onClick = { onChatClick(chat.id) })
                }
            }
        }
    }
}

@Composable
private fun ChatItem(chat: Chat, otherUserName: String, otherUserPhoto: String, onClick: () -> Unit) {
    val photoModel = rememberImageModel(otherUserPhoto)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(modifier = Modifier.size(52.dp)) {
            if (photoModel != null) {
                AsyncImage(
                    model              = photoModel,
                    contentDescription = null,
                    modifier           = Modifier.fillMaxSize().clip(CircleShape)
                )
            } else {
                Box(
                    modifier         = Modifier.fillMaxSize().clip(CircleShape).background(Green500),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        otherUserName.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )
                }
            }
            if (chat.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .align(Alignment.TopEnd)
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    otherUserName,
                    style    = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1
                )
                Text(
                    TimeUtils.formatTime(chat.lastMessageTime),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                chat.productTitle,
                style    = MaterialTheme.typography.bodySmall.copy(color = NeutralGray400),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                chat.lastMessage,
                style    = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (chat.unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Divider(color = NeutralBorder, thickness = 0.5.dp)
}
