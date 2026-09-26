package com.reutiliza.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Usamos la fuente del sistema (sans-serif) para no requerir assets externos.
// Para usar una fuente custom, agrega los .ttf en res/font/ y reemplaza FontFamily.Default.
val ReUtilizaFontFamily = FontFamily.Default

val ReUtilizaTypography = Typography(
    // Títulos de pantalla
    headlineLarge = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        color = NeutralBlack
    ),
    headlineMedium = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = NeutralBlack
    ),
    headlineSmall = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = NeutralBlack
    ),
    // Subtítulos y labels de sección
    titleLarge = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = NeutralBlack
    ),
    titleMedium = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = NeutralBlack
    ),
    // Cuerpo de texto
    bodyLarge = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = NeutralGray700
    ),
    bodyMedium = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = NeutralGray700
    ),
    bodySmall = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = NeutralGray400
    ),
    // Labels de botones y chips
    labelLarge = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ReUtilizaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
)
