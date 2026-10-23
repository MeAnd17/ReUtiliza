package com.reutiliza.app.data.repository

import com.reutiliza.app.data.local.SessionManager
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.remote.LoginRequest
import com.reutiliza.app.data.remote.RegisterRequest
import com.reutiliza.app.data.remote.ReUtilizaApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: ReUtilizaApiService,
    private val session: SessionManager
) {
    val isLoggedIn: Boolean get() = false // se evalúa con sessionManager

    suspend fun isSessionActive(): Boolean = session.getToken() != null

    suspend fun login(email: String, password: String): Result<User> = runCatching {
        val response = api.login(LoginRequest(email, password))
        session.saveSession(response.token, response.user.id)
        response.user
    }

    suspend fun register(name: String, email: String, password: String): Result<User> =
        runCatching {
            val response = api.register(RegisterRequest(name, email, password))
            session.saveSession(response.token, response.user.id)
            response.user
        }

    suspend fun logout() {
        session.clearSession()
    }

    suspend fun getCurrentUser(): Result<User> = runCatching {
        val token = session.getToken() ?: error("No hay sesión activa")
        api.me(session.bearerToken(token))
    }

    suspend fun getCurrentUserId(): String? = session.getUserId()
}
