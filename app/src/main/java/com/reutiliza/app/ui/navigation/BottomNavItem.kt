package com.reutiliza.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Inicio",   Screen.Home.route,    Icons.Filled.Home,    Icons.Outlined.Home),
    BottomNavItem("Explorar", Screen.Explore.route, Icons.Filled.Explore, Icons.Outlined.Explore),
    BottomNavItem("Chats",    Screen.Chats.route,   Icons.Filled.Chat,    Icons.Outlined.Chat),
    BottomNavItem("Perfil",   Screen.Profile.route, Icons.Filled.Person,  Icons.Outlined.Person)
)
