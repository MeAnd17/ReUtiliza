package com.reutiliza.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reutiliza.app.ui.components.GreenLevelCard
import com.reutiliza.app.ui.components.ProductCard
import com.reutiliza.app.ui.components.TopBarReUtiliza
import com.reutiliza.app.ui.theme.Green50
import com.reutiliza.app.ui.theme.Green100
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.ui.theme.NeutralBlack
import com.reutiliza.app.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val user     by vm.currentUser.collectAsState()
    val products by vm.recentProducts.collectAsState()
    val ecoTips  by vm.ecoTips.collectAsState()

    LazyColumn(
        modifier            = Modifier.fillMaxSize().background(Green50),
        contentPadding      = PaddingValues(bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // ── Top Bar ──────────────────────────────────────────────────────────
        item {
            TopBarReUtiliza(
                greenPoints  = user?.greenPoints ?: 0,
                userPhotoUrl = user?.photoUrl ?: ""
            )
        }

        // ── Hero ─────────────────────────────────────────────────────────────
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Dale una segunda",
                    style = MaterialTheme.typography.headlineLarge.copy(color = NeutralBlack)
                )
                Text(
                    "vida a tus objetos",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color      = Green500,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    "Compra, vende, dona o intercambia de forma sostenible",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }

        // ── Level Card ───────────────────────────────────────────────────────
        item {
            user?.let {
                Spacer(Modifier.height(20.dp))
                GreenLevelCard(
                    user     = it,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // ── Acciones rápidas ─────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(24.dp))
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    QuickActionCard("Explorar", "✨",
                        onClick = { /* navegar */ })
                }
                item {
                    QuickActionCard("Publicar", "🌿",
                        onClick = { /* navegar */ })
                }
                item {
                    QuickActionCard("Tendencia", "📈",
                        onClick = { /* navegar */ })
                }
            }
        }

        // ── Cómo funciona ────────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(24.dp))
            Text(
                "¿Cómo funciona?",
                style    = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HowItWorksStep("1", "Publica",  "Sube el objeto que ya no usas",        Modifier.weight(1f))
                HowItWorksStep("2", "Conecta",  "Personas interesadas te contactan",    Modifier.weight(1f))
                HowItWorksStep("3", "Reutiliza","Dale una segunda vida al objeto",      Modifier.weight(1f))
            }
        }

        // ── Eco-Consejos (Consumo API REST con Retrofit) ──────────────────────
        if (ecoTips.isNotEmpty()) {
            item {
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("🌍 Eco-Tips (API REST)", style = MaterialTheme.typography.headlineSmall)
                    Surface(shape = RoundedCornerShape(12.dp), color = Green100) {
                        Text(
                            "Conectado",
                            style = MaterialTheme.typography.labelSmall.copy(color = Green500, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ecoTips) { tip ->
                        Card(
                            modifier  = Modifier.width(260.dp).height(130.dp),
                            shape     = RoundedCornerShape(16.dp),
                            colors    = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text      = tip.title,
                                    style     = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    maxLines  = 1,
                                    color     = NeutralBlack
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text      = tip.body,
                                    style     = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                                    maxLines  = 3
                                )
                            }
                        }
                    }
                }
            }
        }

        // ── Recién publicados ────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(24.dp))
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text("Recién publicados", style = MaterialTheme.typography.headlineSmall)
                TextButton(onClick = { }) {
                    Text("Ver todo →", color = Green500, style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // Grid 2 columnas de productos
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

        if (products.isEmpty()) {
            item {
                Box(
                    modifier           = Modifier.fillMaxWidth().padding(40.dp),
                    contentAlignment   = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🌱", fontSize = 48.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Sé el primero en publicar", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(label: String, emoji: String, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .width(110.dp)
            .height(90.dp)
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 28.sp)
            Spacer(Modifier.height(6.dp))
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun HowItWorksStep(step: String, title: String, desc: String, modifier: Modifier = Modifier) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Green100,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(step, style = MaterialTheme.typography.labelLarge.copy(color = Green500))
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(desc,  style = MaterialTheme.typography.bodySmall)
        }
    }
}
