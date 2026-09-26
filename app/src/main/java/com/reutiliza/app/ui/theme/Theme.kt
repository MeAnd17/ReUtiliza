package com.reutiliza.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary          = Green500,
    onPrimary        = NeutralWhite,
    primaryContainer = Green100,
    onPrimaryContainer = Green500,
    secondary        = Green400,
    onSecondary      = NeutralWhite,
    secondaryContainer = Green100,
    onSecondaryContainer = Green500,
    tertiary         = Green300,
    background       = Green50,
    onBackground     = NeutralBlack,
    surface          = NeutralWhite,
    onSurface        = NeutralBlack,
    surfaceVariant   = NeutralSurface,
    onSurfaceVariant = NeutralGray700,
    outline          = NeutralBorder,
    error            = ColorError,
    onError          = NeutralWhite
)

@Composable
fun ReUtilizaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // La app solo soporta modo claro por ahora (diseño del prototipo es light)
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Green500.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = ReUtilizaTypography,
        content     = content
    )
}
