package com.reutiliza.app.data.repository;

import com.reutiliza.app.data.local.SessionManager;
import com.reutiliza.app.data.model.Chat;
import com.reutiliza.app.data.model.Message;
import com.reutiliza.app.data.model.Product;
import com.reutiliza.app.data.model.User;
import com.reutiliza.app.data.remote.CreateChatRequest;
import com.reutiliza.app.data.remote.ReUtilizaApiService;
import com.reutiliza.app.data.remote.SendMessageRequest;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\fJ6\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\t0\b2\u0006\u0010\u0018\u001a\u00020\fJ,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001e"}, d2 = {"Lcom/reutiliza/app/data/repository/ChatRepository;", "", "api", "Lcom/reutiliza/app/data/remote/ReUtilizaApiService;", "session", "Lcom/reutiliza/app/data/local/SessionManager;", "(Lcom/reutiliza/app/data/remote/ReUtilizaApiService;Lcom/reutiliza/app/data/local/SessionManager;)V", "getChatMessages", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/reutiliza/app/data/model/Message;", "chatId", "", "getOrCreateChat", "Lkotlin/Result;", "buyerUser", "Lcom/reutiliza/app/data/model/User;", "sellerUser", "product", "Lcom/reutiliza/app/data/model/Product;", "getOrCreateChat-BWLJW6A", "(Lcom/reutiliza/app/data/model/User;Lcom/reutiliza/app/data/model/User;Lcom/reutiliza/app/data/model/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserChats", "Lcom/reutiliza/app/data/model/Chat;", "userId", "sendMessage", "", "message", "sendMessage-0E7RQCE", "(Ljava/lang/String;Lcom/reutiliza/app/data/model/Message;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ChatRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.remote.ReUtilizaApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.local.SessionManager session = null;
    
    @javax.inject.Inject()
    public ChatRepository(@org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.remote.ReUtilizaApiService api, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.local.SessionManager session) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.reutiliza.app.data.model.Chat>> getUserChats(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.reutiliza.app.data.model.Message>> getChatMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String chatId) {
        return null;
    }
}