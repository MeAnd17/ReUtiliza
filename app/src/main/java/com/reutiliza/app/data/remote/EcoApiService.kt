package com.reutiliza.app.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EcoApiService {

    /** READ — obtiene los primeros 5 eco-tips */
    @GET("posts?_limit=5")
    suspend fun getEcoTips(): Response<List<EcoTipDto>>

    /** READ ONE — obtiene un eco-tip por id */
    @GET("posts/{id}")
    suspend fun getEcoTipById(@Path("id") id: Int): Response<EcoTipDto>

    /** CREATE — crea un nuevo eco-tip */
    @POST("posts")
    suspend fun createEcoTip(@Body request: EcoTipRequest): Response<EcoTipDto>

    /** UPDATE — actualiza un eco-tip existente */
    @PUT("posts/{id}")
    suspend fun updateEcoTip(
        @Path("id") id: Int,
        @Body request: EcoTipRequest
    ): Response<EcoTipDto>

    /** DELETE — elimina un eco-tip por id */
    @DELETE("posts/{id}")
    suspend fun deleteEcoTip(@Path("id") id: Int): Response<Unit>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
