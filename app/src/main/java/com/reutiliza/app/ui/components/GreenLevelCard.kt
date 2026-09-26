package com.reutiliza.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.model.UserLevel
import com.reutiliza.app.ui.theme.*

@Composable
fun GreenLevelCard(user: User, modifier: Modifier = Modifier) {
    val level      = user.level
    val nextPoints = level.maxPoints.takeIf { it != Int.MAX_VALUE }?.let { it + 1 }
    val progress   = if (nextPoints != null) {
        val range = level.maxPoints - level.minPoints
        if (range > 0) (user.greenPoints - level.minPoints).toFloat() / range else 1f
    } else 1f

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Green500)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Nivel y puntos
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.EmojiEvents,
                        contentDescription = null,
                        tint   = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text  = "Nivel actual",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                        )
                        Text(
                            text  = level.displayName,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color      = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text  = "${user.greenPoints}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color      = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize   = 36.sp
                        )
                    )
                    Text(
                        text  = "puntos verdes",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Barra de progreso
            val pointsToNext = if (nextPoints != null) "${level.maxPoints - user.greenPoints + 1} puntos para el siguiente nivel"
                               else "¡Nivel máximo alcanzado! 🎉"
            Text(
                text  = "Progreso al siguiente nivel",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
            )
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress            = { progress.coerceIn(0f, 1f) },
                modifier            = Modifier.fillMaxWidth().height(8.dp),
                color               = Color.White,
                trackColor          = Color.White.copy(alpha = 0.3f),
                strokeCap           = androidx.compose.ui.graphics.StrokeCap.Round
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text  = pointsToNext,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
            )

            Spacer(Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.3f))
            Spacer(Modifier.height(12.dp))

            // Stats
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(icon = "📋", value = "${user.publishedCount}",  label = "Publicados")
                StatItem(icon = "♻️", value = "${user.reutilizedCount}", label = "Reutilizados")
                StatItem(icon = "🔥", value = "${user.streakDays}",      label = "Racha días")
            }
        }
    }
}

@Composable
private fun StatItem(icon: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 20.sp)
        Spacer(Modifier.height(4.dp))
        Text(
            text  = value,
            style = MaterialTheme.typography.titleLarge.copy(
                color      = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text  = label,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
        )
    }
}
