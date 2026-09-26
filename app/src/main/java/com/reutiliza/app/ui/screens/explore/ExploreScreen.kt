package com.reutiliza.app.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reutiliza.app.data.model.ProductCategory
import com.reutiliza.app.data.model.TransactionType
import com.reutiliza.app.ui.components.ProductCard
import com.reutiliza.app.ui.components.TopBarReUtiliza
import com.reutiliza.app.ui.theme.Green100
import com.reutiliza.app.ui.theme.Green50
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.ui.theme.NeutralBorder
import com.reutiliza.app.viewmodel.ExploreViewModel

@Composable
fun ExploreScreen(
    onProductClick: (String) -> Unit,
    vm: ExploreViewModel = hiltViewModel()
) {
    val products        by vm.products.collectAsState()
    val isLoading       by vm.isLoading.collectAsState()
    val searchQuery     by vm.searchQuery.collectAsState()
    val selCategory     by vm.selectedCategory.collectAsState()
    val selTransaction  by vm.selectedTransactionType.collectAsState()

    var showFilters     by remember { mutableStateOf(false) }
    val focusManager    = LocalFocusManager.current

    LazyColumn(
        modifier            = Modifier.fillMaxSize().background(Green50),
        contentPadding      = PaddingValues(bottom = 88.dp)
    ) {
        item {
            TopBarReUtiliza(title = "Explorar")
        }

        // ── Barra de búsqueda ────────────────────────────────────────────────
        item {
            Row(
                modifier          = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value         = searchQuery,
                    onValueChange = { vm.searchQuery.value = it },
                    placeholder   = { Text("Buscar objetos...") },
                    leadingIcon   = { Icon(Icons.Filled.Search, null) },
                    modifier      = Modifier.weight(1f),
                    singleLine    = true,
                    shape         = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { focusManager.clearFocus(); vm.search() }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Green500,
                        unfocusedBorderColor = NeutralBorder,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor   = Color.White
                    )
                )
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick = { showFilters = !showFilters },
                    modifier = Modifier
                        .background(
                            if (showFilters) Green500 else Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .size(52.dp)
                ) {
                    Icon(
                        Icons.Filled.FilterList,
                        contentDescription = "Filtros",
                        tint = if (showFilters) Color.White else Green500
                    )
                }
            }
        }

        // ── Panel de filtros ─────────────────────────────────────────────────
        if (showFilters) {
            item {
                Card(
                    modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Tipo transacción
                        Text("TIPO DE TRANSACCIÓN", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChipCustom("Todos",        selTransaction == null) { vm.selectTransactionType(null) }
                            FilterChipCustom("Venta",        selTransaction == TransactionType.SALE)     { vm.selectTransactionType(TransactionType.SALE) }
                            FilterChipCustom("Donación",     selTransaction == TransactionType.DONATION) { vm.selectTransactionType(TransactionType.DONATION) }
                            FilterChipCustom("Intercambio",  selTransaction == TransactionType.EXCHANGE) { vm.selectTransactionType(TransactionType.EXCHANGE) }
                        }
                        Spacer(Modifier.height(12.dp))
                        // Estado
                        Text("ORDENAR POR", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChipCustom("Recientes",    true) { vm.search() }
                            FilterChipCustom("Más verdes",   false) { vm.search() }
                        }
                    }
                }
            }
        }

        // ── Categorías ───────────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(8.dp))
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryChip(
                        emoji    = "✨",
                        label    = "Todos",
                        selected = selCategory == null,
                        onClick  = { vm.selectCategory(null) }
                    )
                }
                items(ProductCategory.entries) { cat ->
                    CategoryChip(
                        emoji    = cat.emoji,
                        label    = cat.displayName,
                        selected = selCategory == cat,
                        onClick  = { vm.selectCategory(cat) }
                    )
                }
            }
        }

        // ── Contador de resultados ───────────────────────────────────────────
        item {
            Spacer(Modifier.height(12.dp))
            Text(
                "${products.size} resultados",
                style    = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        // ── Loading ──────────────────────────────────────────────────────────
        if (isLoading) {
            item {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Green500)
                }
            }
        }

        // ── Grid productos ───────────────────────────────────────────────────
        items(products.chunked(2)) { row ->
            Row(
                modifier              = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { product ->
                    ProductCard(
                        product  = product,
                        onClick  = { onProductClick(product.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }

        if (!isLoading && products.isEmpty()) {
            item {
                Box(
                    modifier           = Modifier.fillMaxWidth().padding(60.dp),
                    contentAlignment   = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🔍", fontSize = 48.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("No se encontraron objetos", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(emoji: String, label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick  = onClick,
        label    = { Text("$emoji $label", style = MaterialTheme.typography.labelMedium) },
        colors   = FilterChipDefaults.filterChipColors(
            selectedContainerColor   = Green500,
            selectedLabelColor       = Color.White,
            containerColor           = Color.White
        ),
        shape    = RoundedCornerShape(20.dp)
    )
}

@Composable
fun FilterChipCustom(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick  = onClick,
        label    = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors   = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Green500,
            selectedLabelColor     = Color.White,
            containerColor         = Green100
        ),
        shape = RoundedCornerShape(20.dp)
    )
}
