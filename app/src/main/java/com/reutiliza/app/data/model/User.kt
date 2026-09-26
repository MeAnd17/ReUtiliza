package com.reutiliza.app.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val location: String = "",
    val bio: String = "",
    val greenPoints: Int = 0,
    val level: UserLevel = UserLevel.SEED,
    val publishedCount: Int = 0,
    val reutilizedCount: Int = 0,
    val streakDays: Int = 0,
    val badges: List<String> = emptyList(),   // IDs de insignias desbloqueadas
    val isVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserLevel(val displayName: String, val minPoints: Int, val maxPoints: Int) {
    SEED      ("Semilla Verde",   0,   99),
    SPROUT    ("Brote Verde",   100,  299),
    BRIGHT    ("Verde Brillante", 300, 599),
    GUARDIAN  ("Guardián Verde", 600, 999),
    ECO_MASTER("Eco Maestro",  1000, Int.MAX_VALUE);

    companion object {
        fun fromPoints(points: Int): UserLevel =
            values().lastOrNull { points >= it.minPoints } ?: SEED
    }
}
