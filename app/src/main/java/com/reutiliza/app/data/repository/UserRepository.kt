package com.reutiliza.app.data.repository

import com.reutiliza.app.data.local.SessionManager
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.remote.GreenPointsRequest
import com.reutiliza.app.data.remote.ReUtilizaApiService
import com.reutiliza.app.data.remote.UpdateProfileRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ReUtilizaApiService,
    private val session: SessionManager
) {
    /** Emite el usuario una vez (sin tiempo real — el backend REST no tiene listeners) */
    fun getUserFlow(userId: String): Flow<User> = flow {
        val token = session.getToken() ?: return@flow
        runCatching {
            api.getUser(session.bearerToken(token), userId)
        }.onSuccess { emit(it) }
    }

    suspend fun getUser(userId: String): Result<User> = runCatching {
        val token = session.getToken() ?: error("No autenticado")
        api.getUser(session.bearerToken(token), userId)
    }

    suspend fun updateProfile(userId: String, name: String, bio: String): Result<User> =
        runCatching {
            val token = session.getToken() ?: error("No autenticado")
            api.updateProfile(session.bearerToken(token), userId, UpdateProfileRequest(name, bio))
        }

    suspend fun addGreenPoints(userId: String, points: Int): Result<Unit> = runCatching {
        val token = session.getToken() ?: error("No autenticado")
        api.addGreenPoints(session.bearerToken(token), userId, GreenPointsRequest(points))
    }

    suspend fun getLeaderboard(): Result<List<User>> = runCatching {
        val token = session.getToken() ?: error("No autenticado")
        api.getLeaderboard(session.bearerToken(token))
    }
}
