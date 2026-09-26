package com.reutiliza.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reutiliza.app.ui.theme.Green100
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.ui.theme.NeutralBlack
import com.reutiliza.app.utils.rememberImageModel

/** Top bar estándar de la app con logo, puntos verdes y avatar */
@Composable
fun TopBarReUtiliza(
    title: String = "ReUtiliza",
    greenPoints: Int = 0,
    userPhotoUrl: String = "",
    showPoints: Boolean = true
) {
    val photoModel = rememberImageModel(userPhotoUrl)
    val authUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val initialLetter = (authUser?.displayName?.firstOrNull()
        ?: authUser?.email?.firstOrNull()
        ?: 'A').uppercase()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🌿", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.width(6.dp))
            Text(
                text  = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color      = NeutralBlack,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (showPoints) {
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                    color = Green100
                ) {
                    Row(
                        modifier          = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🌿", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text  = "$greenPoints",
                            style = MaterialTheme.typography.labelLarge.copy(color = Green500)
                        )
                    }
                }
            }

            // Avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Green500),
                contentAlignment = Alignment.Center
            ) {
                if (photoModel != null) {
                    AsyncImage(
                        model              = photoModel,
                        contentDescription = "Avatar",
                        modifier           = Modifier.fillMaxSize().clip(CircleShape)
                    )
                } else {
                    Text(
                        text  = initialLetter,
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
                    )
                }
            }
        }
    }
}
