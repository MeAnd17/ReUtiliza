package com.reutiliza.app.data.repository

import com.reutiliza.app.data.remote.EcoApiService
import com.reutiliza.app.data.remote.EcoTipDto
import com.reutiliza.app.data.remote.EcoTipRequest
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/** Resultado tipado para toda operación con la API */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val type: ErrorType) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

enum class ErrorType {
    NO_CONNECTION,   // sin internet
    TIMEOUT,         // servidor tardó demasiado
    NOT_FOUND,       // 404
    SERVER_ERROR,    // 5xx
    UNKNOWN          // cualquier otro
}

@Singleton
class EcoTipRepository @Inject constructor(
    private val api: EcoApiService
) {

    /** READ — lista de eco-tips */
    suspend fun getEcoTips(): ApiResult<List<EcoTipDto>> = safeCall {
        val response = api.getEcoTips()
        if (response.isSuccessful) {
            ApiResult.Success(response.body() ?: emptyList())
        } else {
            mapHttpError(response.code())
        }
    }

    /** READ ONE */
    suspend fun getEcoTipById(id: Int): ApiResult<EcoTipDto> = safeCall {
        val response = api.getEcoTipById(id)
        if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            mapHttpError(response.code())
        }
    }

    /** CREATE */
    suspend fun createEcoTip(title: String, body: String): ApiResult<EcoTipDto> = safeCall {
        val response = api.createEcoTip(EcoTipRequest(title = title, body = body))
        if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            mapHttpError(response.code())
        }
    }

    /** UPDATE */
    suspend fun updateEcoTip(id: Int, title: String, body: String): ApiResult<EcoTipDto> =
        safeCall {
            val response = api.updateEcoTip(id, EcoTipRequest(title = title, body = body))
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                mapHttpError(response.code())
            }
        }

    /** DELETE */
    suspend fun deleteEcoTip(id: Int): ApiResult<Unit> = safeCall {
        val response = api.deleteEcoTip(id)
        if (response.isSuccessful) {
            ApiResult.Success(Unit)
        } else {
            mapHttpError(response.code())
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private suspend fun <T> safeCall(block: suspend () -> ApiResult<T>): ApiResult<T> {
        return try {
            block()
        } catch (e: UnknownHostException) {
            ApiResult.Error(
                message = "Sin conexión a internet. Verificá tu red.",
                type = ErrorType.NO_CONNECTION
            )
        } catch (e: SocketTimeoutException) {
            ApiResult.Error(
                message = "El servidor tardó demasiado en responder.",
                type = ErrorType.TIMEOUT
            )
        } catch (e: Exception) {
            ApiResult.Error(
                message = "Error inesperado: ${e.localizedMessage}",
                type = ErrorType.UNKNOWN
            )
        }
    }

    private fun mapHttpError(code: Int): ApiResult.Error = when (code) {
        404  -> ApiResult.Error("Recurso no encontrado (404).", ErrorType.NOT_FOUND)
        in 500..599 -> ApiResult.Error("Error en el servidor ($code).", ErrorType.SERVER_ERROR)
        else -> ApiResult.Error("Error HTTP $code.", ErrorType.UNKNOWN)
    }
}
