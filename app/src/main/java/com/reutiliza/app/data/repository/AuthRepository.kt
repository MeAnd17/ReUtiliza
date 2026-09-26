package com.reutiliza.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.reutiliza.app.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser? get() = auth.currentUser
    val isLoggedIn: Boolean get() = auth.currentUser != null

    suspend fun login(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user!!
    }

    suspend fun register(name: String, email: String, password: String): Result<FirebaseUser> =
        runCatching {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!

            // Actualizar nombre en el perfil de Firebase Auth
            runCatching {
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }
                user.updateProfile(profileUpdates).await()
            }

            // Crear documento en Firestore
            val newUser = User(
                id = user.uid,
                name = name,
                email = email,
                greenPoints = 0
            )
            runCatching {
                firestore.collection("users").document(user.uid).set(newUser).await()
            }
            user
        }

    fun logout() = auth.signOut()

    suspend fun getCurrentUserData(): Result<User> = runCatching {
        val uid = auth.currentUser?.uid ?: error("Usuario no autenticado")
        val doc = firestore.collection("users").document(uid).get().await()
        doc.toObject(User::class.java) ?: error("Usuario no encontrado")
    }
}
