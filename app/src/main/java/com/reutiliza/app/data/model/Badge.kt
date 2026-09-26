package com.reutiliza.app.data.model

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val isUnlocked: Boolean = false
)

// Catálogo completo de insignias de la app
object BadgeCatalog {
    val all = listOf(
        Badge("eco_pioneer",   "Eco Pionero",    "Publicaste tu primer artículo",      "🌱"),
        Badge("reutilizer",    "Reutilizador",   "Reutilizaste 5 objetos",             "♻️"),
        Badge("green_bright",  "Verde Brillante","Alcanzaste 200 puntos verdes",       "💚"),
        Badge("donor_hero",    "Héroe Donador",  "Donaste 3 objetos",                  "🎁"),
        Badge("week_streak",   "Racha Semanal",  "Publicaste 7 días seguidos",         "🔥"),
        Badge("chat_starter",  "Conector",       "Iniciaste tu primer chat",           "💬"),
        Badge("community_top", "Top Comunidad",  "Entraste al top 10 del ranking",     "🏆"),
        Badge("eco_master",    "Eco Maestro",    "Alcanzaste 1000 puntos verdes",      "🌍")
    )
}
