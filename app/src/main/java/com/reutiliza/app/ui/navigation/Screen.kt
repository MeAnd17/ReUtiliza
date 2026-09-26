package com.reutiliza.app.ui.navigation

sealed class Screen(val route: String) {
    // Auth
    object Login    : Screen("login")
    object Register : Screen("register")

    // Main (bottom nav)
    object Home    : Screen("home")
    object Explore : Screen("explore")
    object Publish : Screen("publish")
    object Chats   : Screen("chats")
    object Profile : Screen("profile")

    // Detail screens
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object ChatDetail : Screen("chat_detail/{chatId}") {
        fun createRoute(chatId: String) = "chat_detail/$chatId"
    }
}
