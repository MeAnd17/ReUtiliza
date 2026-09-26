package com.reutiliza.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.model.UserLevel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val usersRef = firestore.collection("users")

    fun getUserFlow(userId: String): Flow<User> = callbackFlow {
        val listener = usersRef.document(userId)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    val authUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                    val fallbackName = authUser?.displayName.orEmpty().ifEmpty {
                        authUser?.email?.substringBefore("@") ?: "Usuario"
                    }
                    trySend(User(id = userId, name = fallbackName, email = authUser?.email ?: ""))
                    return@addSnapshotListener
                }
                if (snap != null && snap.exists()) {
                    snap.toObject(User::class.java)?.let { trySend(it) }
                } else {
                    // Si el documento aún no existe en Firestore, crearlo con datos de FirebaseAuth
                    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                    if (currentUser != null && currentUser.uid == userId) {
                        val fallbackName = currentUser.displayName.orEmpty().ifEmpty {
                            currentUser.email?.substringBefore("@") ?: "Usuario"
                        }
                        val newUser = User(
                            id = userId,
                            name = fallbackName,
                            email = currentUser.email ?: "",
                            greenPoints = 0
                        )
                        usersRef.document(userId).set(newUser)
                        trySend(newUser)
                    }
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun getUser(userId: String): Result<User> = runCatching {
        val doc = usersRef.document(userId).get().await()
        if (doc.exists()) {
            doc.toObject(User::class.java) ?: error("Usuario no encontrado")
        } else {
            val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
            val fallbackName = currentUser?.displayName.orEmpty().ifEmpty {
                currentUser?.email?.substringBefore("@") ?: "Usuario"
            }
            val newUser = User(
                id = userId,
                name = fallbackName,
                email = currentUser?.email ?: "",
                greenPoints = 0
            )
            usersRef.document(userId).set(newUser).await()
            newUser
        }
    }

    suspend fun addGreenPoints(userId: String, points: Int): Result<Unit> = runCatching {
        val doc   = usersRef.document(userId).get().await()
        val user  = doc.toObject(User::class.java) ?: return@runCatching
        val newPts= user.greenPoints + points
        val newLvl= UserLevel.fromPoints(newPts)
        usersRef.document(userId).update(
            mapOf("greenPoints" to newPts, "level" to newLvl.name)
        ).await()
    }

    suspend fun unlockBadge(userId: String, badgeId: String): Result<Unit> = runCatching {
        val doc   = usersRef.document(userId).get().await()
        val user  = doc.toObject(User::class.java) ?: return@runCatching
        if (badgeId !in user.badges) {
            usersRef.document(userId).update(
                "badges", user.badges + badgeId
            ).await()
        }
    }

    /** Top 20 usuarios por Puntos Verdes (ranking comunitario) */
    suspend fun getLeaderboard(): Result<List<User>> = runCatching {
        val docs = usersRef
            .orderBy("greenPoints", Query.Direction.DESCENDING)
            .limit(20)
            .get().await()
        docs.mapNotNull { it.toObject(User::class.java) }
    }

    suspend fun updateProfile(userId: String, name: String, bio: String): Result<Unit> =
        runCatching {
            usersRef.document(userId).update(
                mapOf("name" to name, "bio" to bio)
            ).await()
        }
}
