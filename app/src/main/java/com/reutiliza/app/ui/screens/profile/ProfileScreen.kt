package com.reutiliza.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.reutiliza.app.data.model.Badge
import com.reutiliza.app.ui.components.GreenLevelCard
import com.reutiliza.app.ui.components.TopBarReUtiliza
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.utils.rememberImageModel
import com.reutiliza.app.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    vm: ProfileViewModel = hiltViewModel()
) {
    val user   by vm.user.collectAsState()
    val badges by vm.badges.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    val authUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val displayName = user?.name.orEmpty().ifEmpty {
        authUser?.displayName.orEmpty().ifEmpty {
            authUser?.email?.substringBefore("@") ?: "Usuario"
        }
    }
    val initialLetter = (displayName.firstOrNull() ?: 'U').uppercase()

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title            = { Text("Cerrar sesión") },
            text             = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton    = {
                TextButton(onClick = { vm.logout(); onLogout() }) {
                    Text("Salir", color = ColorError)
                }
            },
            dismissButton    = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showEditDialog) {
        var name by remember { mutableStateOf(user?.name ?: "") }
        var bio by remember { mutableStateOf(user?.bio ?: "") }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar perfil") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text("Bio") },
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        vm.updateProfile(name, bio)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Green500)
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    LazyColumn(
        modifier       = Modifier.fillMaxSize().background(Green50),
        contentPadding = PaddingValues(bottom = 88.dp)
    ) {
        item {
            TopBarReUtiliza(
                title        = "Perfil",
                greenPoints  = user?.greenPoints ?: 0,
                userPhotoUrl = user?.photoUrl ?: ""
            )
        }

        // ── Info de usuario ──────────────────────────────────────────────
        item {
            Card(
                modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier          = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Green500),
                        contentAlignment = Alignment.Center
                    ) {
                        val userPhotoModel = rememberImageModel(user?.photoUrl)
                        if (userPhotoModel != null) {
                            AsyncImage(
                                model              = userPhotoModel,
                                contentDescription = null,
                                modifier           = Modifier.fillMaxSize().clip(CircleShape)
                            )
                        } else {
                            Text(
                                initialLetter,
                                style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                            )
                        }
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(displayName, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text(user?.location.orEmpty().ifEmpty { user?.email ?: "" }, style = MaterialTheme.typography.bodySmall.copy(color = NeutralGray400))
                        if (user?.isVerified == true) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.CheckCircle, null, tint = Green500, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Perfil verificado", style = MaterialTheme.typography.labelSmall.copy(color = Green500))
                            }
                        }
                    }
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Filled.Settings, null, tint = NeutralGray400)
                    }
                }
                if (user?.bio?.isNotEmpty() == true) {
                    Text(
                        "\"${user?.bio}\"",
                        style    = MaterialTheme.typography.bodyMedium.copy(
                            color      = NeutralGray700,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
                    )
                }
            }
        }

        // ── Level Card ────────────────────────────────────────────────────
        item {
            user?.let {
                Spacer(Modifier.height(16.dp))
                GreenLevelCard(it, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }

        // ── Insignias ─────────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(20.dp))
            Text(
                "Insignias",
                style    = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(badges) { badge -> BadgeCard(badge) }
            }
        }

        // ── Configuración ─────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(20.dp))
            Text(
                "Configuración",
                style    = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
        }
        item {
            SettingsItem(
                icon  = Icons.Outlined.Notifications,
                title = "Notificaciones",
                desc  = "Gestiona tus alertas",
                onClick = { }
            )
        }
        item {
            SettingsItem(
                icon  = Icons.Outlined.Shield,
                title = "Privacidad",
                desc  = "Controla tu información",
                onClick = { }
            )
        }
        item {
            SettingsItem(
                icon  = Icons.Outlined.Language,
                title = "Idioma",
                desc  = "Español",
                onClick = { }
            )
        }
        item {
            SettingsItem(
                icon  = Icons.Outlined.Help,
                title = "Ayuda",
                desc  = "FAQ y soporte",
                onClick = { }
            )
        }

        // ── Cerrar sesión ─────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(8.dp))
            Card(
                modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                TextButton(
                    onClick  = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                ) {
                    Icon(Icons.Filled.Logout, null, tint = ColorError)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", color = ColorError, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun BadgeCard(badge: Badge) {
    Card(
        modifier  = Modifier.width(110.dp),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) Color.White else Color.White.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(if (badge.isUnlocked) 2.dp else 0.dp)
    ) {
        Column(
            modifier            = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                badge.emoji,
                fontSize = 32.sp,
                color    = if (badge.isUnlocked) Color.Unspecified
                           else Color.Gray.copy(alpha = 0.4f)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                badge.name,
                style     = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                badge.description,
                style     = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color     = NeutralGray400
            )
            Spacer(Modifier.height(4.dp))
            if (badge.isUnlocked) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Check, null, tint = Green500, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(2.dp))
                    Text("Desbloqueada", style = MaterialTheme.typography.labelSmall.copy(color = Green500))
                }
            } else {
                Text("🔒 Bloqueada", style = MaterialTheme.typography.labelSmall.copy(color = NeutralGray400))
            }
        }
    }
}

@Composable
private fun SettingsItem(icon: ImageVector, title: String, desc: String, onClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 3.dp),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        onClick   = onClick
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Green100,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(icon, null, tint = Green500, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(desc,  style = MaterialTheme.typography.bodySmall.copy(color = NeutralGray400))
            }
            Icon(Icons.Filled.ChevronRight, null, tint = NeutralGray400)
        }
    }
}
