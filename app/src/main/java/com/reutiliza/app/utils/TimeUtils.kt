package com.reutiliza.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {
    private val todayFmt  = SimpleDateFormat("HH:mm",  Locale.getDefault())
    private val dateFmt   = SimpleDateFormat("dd/MM",  Locale.getDefault())
    private val fullFmt   = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

    fun formatTime(timestamp: Long): String {
        val now  = System.currentTimeMillis()
        val diff = now - timestamp
        return when {
            diff < 60_000        -> "Ahora"
            diff < 3_600_000     -> "${diff / 60_000}m"
            diff < 86_400_000    -> todayFmt.format(Date(timestamp))
            diff < 604_800_000   -> dateFmt.format(Date(timestamp))
            else                 -> fullFmt.format(Date(timestamp))
        }
    }
}
