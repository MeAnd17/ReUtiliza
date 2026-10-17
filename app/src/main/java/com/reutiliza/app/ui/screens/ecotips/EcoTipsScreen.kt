package com.reutiliza.app.ui.screens.ecotips

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reutiliza.app.data.remote.EcoTipDto
import com.reutiliza.app.data.repository.ErrorType
import com.reutiliza.app.ui.theme.Green100
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.ui.theme.Green50
import com.reutiliza.app.viewmodel.EcoTipsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoTipsScreen(
    vm: EcoTipsViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar mensajes de éxito/error como Snackbar
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            vm.clearMessages()
        }
    }
    LaunchedEffect(state.successMessage) {
        state.successMessage?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            vm.clearMessages()
        }
    }

    // Dialogo crear / editar
    var showDialog by remember { mutableStateOf(false) }
    var editingTip by remember { mutableStateOf<EcoTipDto?>(null) }

    if (showDialog) {
        EcoTipDialog(
            tip      = editingTip,
            onSave   = { title, body ->
                if (editingTip != null) vm.updateTip(editingTip!!.id, title, body)
                else                    vm.createTip(title, body)
                showDialog = false
                editingTip = null
            },
            onDismiss = {
                showDialog = false
                editingTip = null
            }
        )
    }

    // Dialogo confirmación de borrado
    var tipToDelete by remember { mutableStateOf<EcoTipDto?>(null) }
    tipToDelete?.let { tip ->
        AlertDialog(
            onDismissRequest = { tipToDelete = null },
            icon             = { Icon(Icons.Default.Delete, null, tint = Color.Red) },
            title            = { Text("Eliminar Eco-Tip") },
            text             = { Text("¿Seguro que querés eliminar \"${tip.title}\"?") },
            confirmButton    = {
                TextButton(onClick = { vm.deleteTip(tip.id); tipToDelete = null }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton    = {
                TextButton(onClick = { tipToDelete = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("🌍 Eco-Tips", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green500,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { vm.loadTips() }) {
                        Icon(Icons.Default.Refresh, "Recargar", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick           = { editingTip = null; showDialog = true },
                containerColor    = Green500,
                contentColor      = Color.White
            ) {
                Icon(Icons.Default.Add, "Nuevo Eco-Tip")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Green50)
                .padding(padding)
        ) {
            when {
                // ── Estado de carga ───────────────────────────────────────────
                state.isLoading -> {
                    Column(
                        modifier            = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = Green500)
                        Text(
                            "Conectando con el servidor...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // ── Sin conexión ──────────────────────────────────────────────
                state.isOffline -> {
                    Column(
                        modifier            = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.WifiOff,
                            contentDescription = null,
                            modifier           = Modifier.size(64.dp),
                            tint               = Color.Gray
                        )
                        Text(
                            "Sin conexión a internet",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Revisá tu red e intentá nuevamente.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Button(
                            onClick        = { vm.loadTips() },
                            colors         = ButtonDefaults.buttonColors(containerColor = Green500)
                        ) {
                            Icon(Icons.Default.Refresh, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Reintentar")
                        }
                    }
                }

                // ── Lista vacía ───────────────────────────────────────────────
                state.tips.isEmpty() -> {
                    Column(
                        modifier            = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("🌱", style = MaterialTheme.typography.displaySmall)
                        Text("No hay eco-tips todavía", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            "Presioná + para crear el primero",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // ── Lista con datos ───────────────────────────────────────────
                else -> {
                    LazyColumn(
                        contentPadding      = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Banner de estado conectado
                        item {
                            Row(
                                modifier          = Modifier
                                    .fillMaxWidth()
                                    .background(Green100, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, null, tint = Green500, modifier = Modifier.size(18.dp))
                                Text(
                                    "API conectada — ${state.tips.size} registros obtenidos",
                                    style = MaterialTheme.typography.labelMedium.copy(color = Green500, fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        items(state.tips, key = { it.id }) { tip ->
                            EcoTipCard(
                                tip       = tip,
                                onEdit    = { editingTip = it; showDialog = true },
                                onDelete  = { tipToDelete = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Tarjeta de Eco-Tip ────────────────────────────────────────────────────────

@Composable
private fun EcoTipCard(
    tip: EcoTipDto,
    onEdit: (EcoTipDto) -> Unit,
    onDelete: (EcoTipDto) -> Unit
) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier          = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Green100
                    ) {
                        Text(
                            "ID: ${tip.id}",
                            style    = MaterialTheme.typography.labelSmall.copy(color = Green500),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        tip.title,
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines   = 2
                    )
                }
                Row {
                    IconButton(onClick = { onEdit(tip) }, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Edit, "Editar", tint = Green500, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = { onDelete(tip) }, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red, modifier = Modifier.size(18.dp))
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                tip.body,
                style    = MaterialTheme.typography.bodySmall,
                color    = Color.Gray,
                maxLines = 3
            )
        }
    }
}

// ── Dialogo Crear / Editar ────────────────────────────────────────────────────

@Composable
private fun EcoTipDialog(
    tip: EcoTipDto?,
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(tip?.title ?: "") }
    var body  by remember { mutableStateOf(tip?.body  ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (tip == null) "Nuevo Eco-Tip" else "Editar Eco-Tip")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = title,
                    onValueChange = { title = it },
                    label         = { Text("Título") },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value         = body,
                    onValueChange = { body = it },
                    label         = { Text("Descripción") },
                    minLines      = 3,
                    modifier      = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick        = { onSave(title, body) },
                colors         = ButtonDefaults.buttonColors(containerColor = Green500),
                enabled        = title.isNotBlank() && body.isNotBlank()
            ) {
                Text(if (tip == null) "Crear" else "Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
