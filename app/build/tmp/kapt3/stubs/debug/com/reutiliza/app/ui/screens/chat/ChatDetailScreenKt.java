package com.reutiliza.app.ui.screens.chat;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.reutiliza.app.data.model.Message;
import com.reutiliza.app.ui.theme.*;
import com.reutiliza.app.utils.TimeUtils;
import com.reutiliza.app.viewmodel.ChatViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0018\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0003\u00a8\u0006\r"}, d2 = {"ChatDetailScreen", "", "chatId", "", "onBack", "Lkotlin/Function0;", "vm", "Lcom/reutiliza/app/viewmodel/ChatViewModel;", "MessageBubble", "message", "Lcom/reutiliza/app/data/model/Message;", "isFromMe", "", "app_debug"})
public final class ChatDetailScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ChatDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String chatId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.viewmodel.ChatViewModel vm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MessageBubble(com.reutiliza.app.data.model.Message message, boolean isFromMe) {
    }
}