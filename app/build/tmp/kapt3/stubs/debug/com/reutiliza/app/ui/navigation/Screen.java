package com.reutiliza.app.ui.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\t\u0007\b\t\n\u000b\f\r\u000e\u000fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\t\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "ChatDetail", "Chats", "Explore", "Home", "Login", "ProductDetail", "Profile", "Publish", "Register", "Lcom/reutiliza/app/ui/navigation/Screen$ChatDetail;", "Lcom/reutiliza/app/ui/navigation/Screen$Chats;", "Lcom/reutiliza/app/ui/navigation/Screen$Explore;", "Lcom/reutiliza/app/ui/navigation/Screen$Home;", "Lcom/reutiliza/app/ui/navigation/Screen$Login;", "Lcom/reutiliza/app/ui/navigation/Screen$ProductDetail;", "Lcom/reutiliza/app/ui/navigation/Screen$Profile;", "Lcom/reutiliza/app/ui/navigation/Screen$Publish;", "Lcom/reutiliza/app/ui/navigation/Screen$Register;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$ChatDetail;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "createRoute", "", "chatId", "app_debug"})
    public static final class ChatDetail extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.ChatDetail INSTANCE = null;
        
        private ChatDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String chatId) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Chats;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Chats extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Chats INSTANCE = null;
        
        private Chats() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Explore;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Explore extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Explore INSTANCE = null;
        
        private Explore() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Home;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Home extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Login;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Login extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Login INSTANCE = null;
        
        private Login() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$ProductDetail;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "createRoute", "", "productId", "app_debug"})
    public static final class ProductDetail extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.ProductDetail INSTANCE = null;
        
        private ProductDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String productId) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Profile;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Profile extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Profile INSTANCE = null;
        
        private Profile() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Publish;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Publish extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Publish INSTANCE = null;
        
        private Publish() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/reutiliza/app/ui/navigation/Screen$Register;", "Lcom/reutiliza/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Register extends com.reutiliza.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.reutiliza.app.ui.navigation.Screen.Register INSTANCE = null;
        
        private Register() {
        }
    }
}