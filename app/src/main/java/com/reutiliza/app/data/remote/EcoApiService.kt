package com.reutiliza.app.data.remote

import retrofit2.http.GET

interface EcoApiService {
    @GET("posts?_limit=5")
    suspend fun getEcoTips(): List<EcoTipDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
