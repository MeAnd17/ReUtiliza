package com.reutiliza.app.utils

import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberImageModel(url: String?): Any? {
    return remember(url) {
        if (url.isNullOrBlank()) return@remember null
        if (url.startsWith("data:image/")) {
            runCatching {
                val base64Data = url.substringAfter("base64,")
                Base64.decode(base64Data, Base64.NO_WRAP)
            }.getOrNull()
        } else {
            url
        }
    }
}
