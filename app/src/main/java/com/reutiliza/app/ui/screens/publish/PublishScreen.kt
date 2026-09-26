package com.reutiliza.app.ui.screens.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.ProductCondition
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.ui.theme.*
import com.reutiliza.app.viewmodel.PublishUiState
import com.reutiliza.app.viewmodel.PublishViewModel

@Composable
fun PublishScreen(
    onPublishSuccess: () -> Unit,
    onBack: () -> Unit,
    vm: PublishViewModel = hiltViewModel()
) {
    val context  = LocalContext.current
    val uiState  by vm.uiState.collectAsState()
    val images   by vm.selectedImages.collectAsState()
    val title    by vm.title.collectAsState()
    val desc     by vm.description.collectAsState()
    val price    by vm.price.collectAsState()
    val category by vm.selectedCategory.collectAsState()
    val txType   by vm.selectedTransactionType.collectAsState()
    val condition by vm.selectedCondition.collectAsState()

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> -> uris.forEach { vm.addImage(it) } }

    LaunchedEffect(uiState) {
        if (uiState is PublishUiState.Success) {
            vm.resetState()
            onPublishSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green50)
            .imePadding()
    ) {
        // Top Bar
        Row(
            modifier          = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
            }
            Text("🌿", fontSize = 20.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                "Publicar artículo",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.weight(1f))
            // Puntos verdes preview
            Surface(shape = RoundedCornerShape(20.dp), color = Green100) {
                Text(
                    "🌿 ${if (txType == TransactionType.DONATION) "+30" else if (txType == TransactionType.EXCHANGE) "+20" else "+15"}",
                    style    = MaterialTheme.typography.labelLarge.copy(color = Green500),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // ── Fotos ─────────────────────────────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth().height(140.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                border    = androidx.compose.foundation.BorderStroke(1.dp, NeutralBorder)
            ) {
                if (images.isEmpty()) {
                    Box(
                        modifier           = Modifier.fillMaxSize().clickable { imagePicker.launch("image/*") },
                        contentAlignment   = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.CameraAlt, null, tint = NeutralGray400, modifier = Modifier.size(36.dp))
                            Spacer(Modifier.height(6.dp))
                            Text("Añadir fotos",   style = MaterialTheme.typography.titleMedium.copy(color = NeutralGray700))
                            Text("Máximo 5 fotos", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                } else {
                    LazyRow(
                        contentPadding        = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier              = Modifier.fillMaxSize()
                    ) {
                        items(images) { uri ->
                            Box {
                                AsyncImage(
                                    model              = uri,
                                    contentDescription = null,
                                    contentScale       = ContentScale.Crop,
                                    modifier           = Modifier
                                        .size(110.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                IconButton(
                                    onClick  = { vm.removeImage(uri) },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(Icons.Filled.Close, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                        if (images.size < 5) {
                            item {
                                Box(
                                    modifier           = Modifier
                                        .size(110.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Green100)
                                        .clickable { imagePicker.launch("image/*") },
                                    contentAlignment   = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.Add, null, tint = Green500)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Tipo de transacción ────────────────────────────────────────────
            SectionLabel("TIPO DE TRANSACCIÓN *")
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TransactionType.entries.forEach { type ->
                    val (emoji, label) = when (type) {
                        TransactionType.SALE     -> "💰" to "Vender"
                        TransactionType.DONATION -> "🎁" to "Donar"
                        TransactionType.EXCHANGE -> "🔄" to "Intercambiar"
                    }
                    TransactionTypeButton(
                        emoji    = emoji,
                        label    = label,
                        selected = txType == type,
                        onClick  = { vm.selectedTransactionType.value = type },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Título ─────────────────────────────────────────────────────────
            SectionLabel("TÍTULO DEL ARTÍCULO *")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value         = title,
                onValueChange = { vm.title.value = it },
                placeholder   = { Text("Ej: Chaqueta de mezclilla") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true,
                shape         = RoundedCornerShape(12.dp),
                colors        = outlinedFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            // ── Descripción ────────────────────────────────────────────────────
            SectionLabel("DESCRIPCIÓN")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value         = desc,
                onValueChange = { vm.description.value = it },
                placeholder   = { Text("Describe el estado, tamaño, marca...") },
                modifier      = Modifier.fillMaxWidth().height(120.dp),
                shape         = RoundedCornerShape(12.dp),
                colors        = outlinedFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            // ── Categoría ──────────────────────────────────────────────────────
            SectionLabel("CATEGORÍA *")
            Spacer(Modifier.height(8.dp))
            val categories = ProductCategory.entries
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.chunked(4).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { cat ->
                            CategoryButton(
                                emoji    = cat.emoji,
                                label    = cat.displayName,
                                selected = category == cat,
                                onClick  = { vm.selectedCategory.value = cat },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Condición ──────────────────────────────────────────────────────
            SectionLabel("CONDICIÓN *")
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ProductCondition.entries.forEach { cond ->
                    FilterChip(
                        selected = condition == cond,
                        onClick  = { vm.selectedCondition.value = cond },
                        label    = { Text(cond.displayName, style = MaterialTheme.typography.labelSmall) },
                        colors   = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green500,
                            selectedLabelColor     = Color.White
                        ),
                        shape    = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ── Precio (solo si es venta) ──────────────────────────────────────
            if (txType == TransactionType.SALE) {
                Spacer(Modifier.height(16.dp))
                SectionLabel("PRECIO (€) *")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value         = price,
                    onValueChange = { vm.price.value = it },
                    placeholder   = { Text("0.00") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(12.dp),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    ),
                    colors = outlinedFieldColors()
                )
            }

            Spacer(Modifier.height(16.dp))
        }

        // ── Botón publicar ──────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                if (uiState is PublishUiState.Error) {
                    Text(
                        text     = (uiState as PublishUiState.Error).message,
                        color    = MaterialTheme.colorScheme.error,
                        style    = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick  = { vm.publish(context) },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = Green500),
                    enabled  = uiState !is PublishUiState.Loading
                ) {
                    if (uiState is PublishUiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Icon(Icons.Filled.CheckCircle, null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Publicar artículo", style = MaterialTheme.typography.labelLarge.copy(color = Color.White))
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelSmall.copy(color = NeutralGray400))
}

@Composable
private fun TransactionTypeButton(
    emoji: String, label: String, selected: Boolean,
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier
            .height(80.dp)
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(
            containerColor = if (selected) Green500 else Color.White
        ),
        border    = if (selected) null else
            androidx.compose.foundation.BorderStroke(1.dp, NeutralBorder)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (selected) Color.White else NeutralBlack
                )
            )
        }
    }
}

@Composable
private fun CategoryButton(
    emoji: String, label: String, selected: Boolean,
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier
            .height(72.dp)
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(
            containerColor = if (selected) Green100 else Color.White
        ),
        border    = if (selected)
            androidx.compose.foundation.BorderStroke(1.5.dp, Green500)
        else
            androidx.compose.foundation.BorderStroke(1.dp, NeutralBorder)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 20.sp)
            Spacer(Modifier.height(2.dp))
            Text(
                label,
                style   = MaterialTheme.typography.labelSmall.copy(
                    color = if (selected) Green500 else NeutralBlack
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = Green500,
    unfocusedBorderColor    = NeutralBorder,
    unfocusedContainerColor = Color.White,
    focusedContainerColor   = Color.White
)
