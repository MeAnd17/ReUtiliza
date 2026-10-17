package com.reutiliza.app.data.remote

/** DTO de respuesta — lo que devuelve la API */
data class EcoTipDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int = 1
)

/** DTO de petición — lo que enviamos en POST y PUT */
data class EcoTipRequest(
    val title: String,
    val body: String,
    val userId: Int = 1
)
