package com.reutiliza.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reutiliza.app.data.model.Product
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.utils.rememberImageModel

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val firstImage = product.imageUrls.firstOrNull()
    val imageModel = rememberImageModel(firstImage)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // ── Imagen ────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                if (imageModel != null) {
                    AsyncImage(
                        model              = imageModel,
                        contentDescription = product.title,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Green100)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(product.category.emoji, fontSize = 48.sp)
                    }
                }

                // Etiqueta de tipo de transacción
                TransactionBadge(
                    type     = product.transactionType,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                )

                // Puntos verdes
                GreenPointsChip(
                    points   = product.greenPointsReward,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                )

                // Favorito
                IconButton(
                    onClick   = { },
                    modifier  = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector        = if (product.isFavorite) Icons.Filled.Favorite
                                             else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint               = if (product.isFavorite) ColorError else Color.White
                    )
                }
            }

            // ── Info ──────────────────────────────────────────────────────────
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        text      = product.title,
                        style     = MaterialTheme.typography.titleMedium,
                        maxLines  = 1,
                        overflow  = TextOverflow.Ellipsis,
                        modifier  = Modifier.weight(1f)
                    )
                    ConditionChip(condition = product.condition.displayName)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text  = "📍 ${product.location}",
                        style = MaterialTheme.typography.bodySmall,
                        color = NeutralGray400
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Precio
                if (product.transactionType == TransactionType.DONATION) {
                    Text(
                        text  = "Gratis",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color      = ColorDonation
                        )
                    )
                } else if (product.price > 0) {
                    Text(
                        text  = "${product.price.toInt()}€",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionBadge(type: TransactionType, modifier: Modifier = Modifier) {
    val (color, text) = when (type) {
        TransactionType.DONATION -> Pair(ColorDonation, "🎁 Donación")
        TransactionType.SALE     -> Pair(ColorSale,     "🏷 Venta")
        TransactionType.EXCHANGE -> Pair(ColorExchange, "🔄 Intercambio")
    }
    Surface(
        modifier  = modifier,
        shape     = RoundedCornerShape(20.dp),
        color     = color.copy(alpha = 0.92f),
        tonalElevation = 0.dp
    ) {
        Text(
            text      = text,
            style     = MaterialTheme.typography.labelSmall,
            color     = Color.White,
            modifier  = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun GreenPointsChip(points: Int, modifier: Modifier = Modifier) {
    if (points <= 0) return
    Surface(
        modifier  = modifier,
        shape     = RoundedCornerShape(20.dp),
        color     = Color.White.copy(alpha = 0.9f)
    ) {
        Text(
            text     = "🌿 +$points",
            style    = MaterialTheme.typography.labelSmall.copy(color = Green500),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ConditionChip(condition: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(8.dp),
        color    = Green100
    ) {
        Text(
            text     = condition,
            style    = MaterialTheme.typography.labelSmall.copy(color = Green500),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}
