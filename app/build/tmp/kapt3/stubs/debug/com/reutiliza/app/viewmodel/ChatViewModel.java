package com.reutiliza.app.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.reutiliza.app.data.model.Chat;
import com.reutiliza.app.data.model.Message;
import com.reutiliza.app.data.model.MessageType;
import com.reutiliza.app.data.model.User;
import com.reutiliza.app.data.repository.ChatRepository;
import com.reutiliza.app.data.repository.UserRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\"2\b\u0010#\u001a\u0004\u0018\u00010\fJ\b\u0010$\u001a\u00020%H\u0002J\u000e\u0010&\u001a\u00020%2\u0006\u0010\'\u001a\u00020\u0011J\u000e\u0010(\u001a\u00020%2\u0006\u0010\'\u001a\u00020\u0011R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R#\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/reutiliza/app/viewmodel/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "chatRepo", "Lcom/reutiliza/app/data/repository/ChatRepository;", "userRepo", "Lcom/reutiliza/app/data/repository/UserRepository;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "(Lcom/reutiliza/app/data/repository/ChatRepository;Lcom/reutiliza/app/data/repository/UserRepository;Lcom/google/firebase/auth/FirebaseAuth;)V", "_chats", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/reutiliza/app/data/model/Chat;", "_messages", "Lcom/reutiliza/app/data/model/Message;", "_otherUsers", "", "", "Lcom/reutiliza/app/data/model/User;", "chats", "Lkotlinx/coroutines/flow/StateFlow;", "getChats", "()Lkotlinx/coroutines/flow/StateFlow;", "currentUserId", "getCurrentUserId", "()Ljava/lang/String;", "messageInput", "getMessageInput", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "messages", "getMessages", "otherUsers", "getOtherUsers", "getOtherUserForChat", "Lkotlin/Pair;", "chat", "loadChats", "", "loadMessages", "chatId", "sendMessage", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.ChatRepository chatRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.UserRepository userRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.reutiliza.app.data.model.Chat>> _chats = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.reutiliza.app.data.model.Chat>> chats = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.reutiliza.app.data.model.Message>> _messages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.reutiliza.app.data.model.Message>> messages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.reutiliza.app.data.model.User>> _otherUsers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.reutiliza.app.data.model.User>> otherUsers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> messageInput = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.ChatRepository chatRepo, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.UserRepository userRepo, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth auth) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.reutiliza.app.data.model.Chat>> getChats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.reutiliza.app.data.model.Message>> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.reutiliza.app.data.model.User>> getOtherUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getMessageInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentUserId() {
        return null;
    }
    
    private final void loadChats() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.String, java.lang.String> getOtherUserForChat(@org.jetbrains.annotations.Nullable()
    com.reutiliza.app.data.model.Chat chat) {
        return null;
    }
    
    public final void loadMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String chatId) {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String chatId) {
    }
}