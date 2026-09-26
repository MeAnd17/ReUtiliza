package com.reutiliza.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.ui.components.GreenPointsChip
import com.reutiliza.app.ui.components.TransactionBadge
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.utils.rememberImageModel
import com.reutiliza.app.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit,
    onChatClick: (String) -> Unit,
    vm: ProductDetailViewModel = hiltViewModel()
) {
    val product   by vm.product.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val chatId    by vm.chatId.collectAsState()
    val currentUserId = vm.currentUserId

    LaunchedEffect(productId) { vm.loadProduct(productId) }
    LaunchedEffect(chatId) {
        chatId?.let { id ->
            vm.clearChatId()
            onChatClick(id)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Green50).imePadding()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color    = Green500
            )
        } else if (product == null) {
            Column(
                modifier            = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🤔", fontSize = 48.sp)
                Text("Producto no encontrado", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            val p = product!!
            val isOwner = currentUserId != null && currentUserId == p.ownerId
            var selectedImageIndex by remember(p.id) { mutableIntStateOf(0) }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // ── Imágenes ───────────────────────────────────────────────
                Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                    val currentImageUrl = p.imageUrls.getOrNull(selectedImageIndex) ?: p.imageUrls.firstOrNull()
                    val heroImageModel = rememberImageModel(currentImageUrl)

                    if (heroImageModel != null) {
                        AsyncImage(
                            model              = heroImageModel,
                            contentDescription = p.title,
                            contentScale       = ContentScale.Crop,
                            modifier           = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Green100),
                            contentAlignment = Alignment.Center
                        ) { Text(p.category.emoji, fontSize = 64.sp) }
                    }

                    // Back button
                    IconButton(
                        onClick  = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color.White.copy(alpha = 0.85f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás")
                    }

                    // Favorito
                    IconButton(
                        onClick  = { vm.toggleFavorite() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color.White.copy(alpha = 0.85f), CircleShape)
                    ) {
                        Icon(
                            if (p.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            null,
                            tint = if (p.isFavorite) ColorError else NeutralGray700
                        )
                    }

                    // Miniaturas interactiva si hay más fotos
                    if (p.imageUrls.size > 1) {
                        LazyRow(
                            modifier       = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(p.imageUrls) { index, url ->
                                val thumbModel = rememberImageModel(url)
                                val isSelected = index == selectedImageIndex
                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(
                                            width = if (isSelected) 2.5.dp else 1.dp,
                                            color = if (isSelected) Green500 else Color.White.copy(alpha = 0.8f),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable { selectedImageIndex = index }
                                ) {
                                    AsyncImage(
                                        model              = thumbModel,
                                        contentDescription = null,
                                        contentScale       = ContentScale.Crop,
                                        modifier           = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }

                // ── Info ───────────────────────────────────────────────────
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    // Tipo + puntos
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        TransactionBadge(type = p.transactionType)
                        GreenPointsChip(points = p.greenPointsReward)
                    }

                    Spacer(Modifier.height(10.dp))

                    // Título
                    Text(
                        p.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(Modifier.height(6.dp))

                    // Precio
                    if (p.transactionType == TransactionType.DONATION) {
                        Text(
                            "Gratis",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = ColorDonation, fontWeight = FontWeight.ExtraBold
                            )
                        )
                    } else if (p.price > 0) {
                        Text(
                            "${p.price.toInt()}€",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Condición + categoría
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(shape = RoundedCornerShape(8.dp), color = Green100) {
                            Text(
                                p.condition.displayName,
                                style    = MaterialTheme.typography.labelMedium.copy(color = Green500),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = NeutralSurface) {
                            Text(
                                "${p.category.emoji} ${p.category.displayName}",
                                style    = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Divider(color = NeutralBorder)
                    Spacer(Modifier.height(12.dp))

                    // Descripción
                    Text("Descripción", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Spacer(Modifier.height(6.dp))
                    Text(
                        p.description.ifEmpty { "Sin descripción adicional." },
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(16.dp))
                    Divider(color = NeutralBorder)
                    Spacer(Modifier.height(12.dp))

                    // Vendedor
                    val authUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                    val ownerDisplayName = if (p.ownerName.isNotBlank() && p.ownerName != "Usuario") {
                        p.ownerName
                    } else if (isOwner) {
                        authUser?.displayName.orEmpty().ifEmpty {
                            authUser?.email?.substringBefore("@") ?: "Usuario"
                        }
                    } else {
                        p.ownerName.ifEmpty { "Usuario" }
                    }

                    Text("Publicado por", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(44.dp).clip(CircleShape).background(Green500),
                            contentAlignment = Alignment.Center
                        ) {
                            val ownerPhotoModel = rememberImageModel(p.ownerPhotoUrl)
                            if (ownerPhotoModel != null) {
                                AsyncImage(
                                    model              = ownerPhotoModel,
                                    contentDescription = null,
                                    modifier           = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            } else {
                                Text(
                                    ownerDisplayName.firstOrNull()?.uppercase() ?: "?",
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                                )
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(ownerDisplayName, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "📍 ${p.location.ifEmpty { "Ubicación" }}",
                                style = MaterialTheme.typography.bodySmall.copy(color = NeutralGray400)
                            )
                        }
                    }
                }

                // ── Botón Contactar / Tu publicación ────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    if (isOwner) {
                        Surface(
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape    = RoundedCornerShape(16.dp),
                            color    = Green100
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "📌 Tu publicación",
                                    style = MaterialTheme.typography.titleMedium.copy(color = Green500, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick  = { vm.contactSeller() },
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape    = RoundedCornerShape(16.dp),
                            colors   = ButtonDefaults.buttonColors(containerColor = Green500)
                        ) {
                            Icon(Icons.Filled.Chat, null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                when (p.transactionType) {
                                    TransactionType.DONATION -> "Solicitar donación"
                                    TransactionType.EXCHANGE -> "Proponer intercambio"
                                    TransactionType.SALE     -> "Contactar vendedor"
                                },
                                style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}
