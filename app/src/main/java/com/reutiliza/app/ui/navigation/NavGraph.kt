package com.reutiliza.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.reutiliza.app.ui.screens.auth.LoginScreen
import com.reutiliza.app.ui.screens.auth.RegisterScreen
import com.reutiliza.app.ui.screens.chat.ChatDetailScreen
import com.reutiliza.app.ui.screens.chat.ChatsScreen
import com.reutiliza.app.ui.screens.detail.ProductDetailScreen
import com.reutiliza.app.ui.screens.explore.ExploreScreen
import com.reutiliza.app.ui.screens.home.HomeScreen
import com.reutiliza.app.ui.screens.profile.ProfileScreen
import com.reutiliza.app.ui.screens.publish.PublishScreen
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.ui.theme.NeutralGray400

@Composable
fun ReUtilizaNavGraph(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val startDest = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    // Rutas que muestran la bottom bar
    val mainRoutes = setOf(
        Screen.Home.route,
        Screen.Explore.route,
        Screen.Chats.route,
        Screen.Profile.route,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = androidx.compose.ui.unit.Dp(8f)
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = navBackStackEntry?.destination?.hierarchy
                            ?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor   = Green500,
                                selectedTextColor   = Green500,
                                unselectedIconColor = NeutralGray400,
                                unselectedTextColor = NeutralGray400,
                                indicatorColor      = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.Publish.route) },
                    containerColor = Green500,
                    contentColor   = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Publicar")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController  = navController,
            startDestination = startDest,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ── Auth ──────────────────────────────────────────────────────────
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }

            // ── Main ──────────────────────────────────────────────────────────
            composable(Screen.Home.route) {
                HomeScreen(
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.Explore.route) {
                ExploreScreen(
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.Publish.route) {
                PublishScreen(
                    onPublishSuccess = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Chats.route) {
                ChatsScreen(
                    onChatClick = { chatId ->
                        navController.navigate(Screen.ChatDetail.createRoute(chatId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // ── Detail ────────────────────────────────────────────────────────
            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStack ->
                val productId = backStack.arguments?.getString("productId") ?: ""
                ProductDetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() },
                    onChatClick = { chatId ->
                        navController.navigate(Screen.ChatDetail.createRoute(chatId))
                    }
                )
            }
            composable(
                route = Screen.ChatDetail.route,
                arguments = listOf(navArgument("chatId") { type = NavType.StringType })
            ) { backStack ->
                val chatId = backStack.arguments?.getString("chatId") ?: ""
                ChatDetailScreen(
                    chatId = chatId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
